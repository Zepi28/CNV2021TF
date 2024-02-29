public class MessageReceiveHandler implements MessageReceiver {

    @override
    public void receiveMessage(PubsubMessage msg, AckReplyConsumer ackReply) {
        String message = msg.getData().toStringUTF8();

        if(InqUtils.isTextAnswer(message)) {
            TextReponse txt = InqUtils.parseText(message);
            db.collection("response").set(txt);
        } else {
            ChoiceResponse txt = InqUtils.parseChoice(message);
            db.collection("response").set(txt);
        }
        pubmsg.ack();
    }

}
