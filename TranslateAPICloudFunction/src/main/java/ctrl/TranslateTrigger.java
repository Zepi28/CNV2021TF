package ctrl;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.events.cloud.pubsub.v1.Message;
import firestore.Database;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TranslateTrigger implements BackgroundFunction<Message> {
    /**
     * Traduction Variables
     **/
    public static final String TARGET_LANGUAGE = "pt";
    public static final String SOURCE_LANGUAGE = "en";
    private static final Logger logger = Logger.getLogger(TranslateTrigger.class.getName());

    @Override
    public void accept(Message message, Context context) {
        if (message.getData() == null) {
            logger.log(Level.WARNING,"No message provided");
            return;
        }

        String messageString = new String(
                Base64.getDecoder().decode(message.getData().getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
        logger.info("Started with message : " + messageString);
        try {
            run(messageString);
        } catch (Exception exception) {
            logger.log(Level.SEVERE,exception.getMessage());
        }
    }


    public void run(String id) throws Exception {
        try {
            Database.init();
            List<PairLabel> labels = obtainLabels(id); //obtem as labels de "VisionResults"
            List<PairLabel> translatedLabels = translateLabels(labels); //Traduz
            Database.insertToDatabase(translatedLabels, id); //Mete em "TranslateResults" o resultado
        } catch (ClassCastException e) {
            //Cast could not be completed when the document has "Error" field coming from Vision API
            //to make sure the error is inserted on both collections, we insertError to the new collection TRANSLATE_RESULTS
            //so the client only needs to check TRANSLATE_RESULTS collection for errors/results.
            logger.log(Level.WARNING,"Error came from VISION Api!");
            Database.insertErrorToDatabase(e.getMessage(), id);
        } catch (Exception e) {
            logger.log(Level.WARNING,"Error with Translate Project");
            Database.insertErrorToDatabase(e.getMessage(), id);
            throw e;
        } finally {
            Database.close();
        }
    }

    /**
     * @param id Obtain labels from firestore (VisionResults) from document ID
     */
    public static List<PairLabel> obtainLabels(String id) throws Exception {
        DocumentSnapshot documentSnapshot = Database.getDocumentSnapshot(id, Database.LABELS_COLLECTION);
        Map<String, Object> map = documentSnapshot.getData();
        if (map == null)
            throw new NullPointerException("Not a valid document with id : " + id + " on " + Database.LABELS_COLLECTION);
        List<PairLabel> labels = new ArrayList<>();
        for (String label : map.keySet()) {
            try {
                ArrayList<Object> arr = (ArrayList<Object>) map.get(label); //Obtains array
                double score = (double) arr.get(0); //Obtains the first (and only , for now) member , which is a Double
                labels.add(new PairLabel(label, score));
            } catch (ClassCastException e) {
                //throws with the Error message coming from VISION API
                throw new ClassCastException((String) map.get(label));
            }
        }
        return labels;
    }

    public static List<PairLabel> translateLabels(List<PairLabel> labels) {
        List<PairLabel> labelsTranslated = new ArrayList<>();
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        for (PairLabel label : labels) {
            Translation translation = translate.translate(
                    label.label,
                    Translate.TranslateOption.sourceLanguage(SOURCE_LANGUAGE),
                    Translate.TranslateOption.targetLanguage(TARGET_LANGUAGE)
            );
            labelsTranslated.add(new PairLabel(translation.getTranslatedText(), label.score));
        }
        return labelsTranslated;
    }
}