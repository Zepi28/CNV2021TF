package httptrigger;

import com.google.cloud.compute.v1.Instance;
import com.google.cloud.compute.v1.InstancesClient;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;

public class ShowIPs implements HttpFunction {
    private static final String PROJECT_ID = "g08-t3n-v2021";
    private final Database db = new Database();

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        db.setParametersFromDB();
        BufferedWriter writer = response.getWriter();
        try {
            listVMInstances(writer);
        } catch (Exception e) {
            response.setStatusCode(404); //"No Content"
        }
        writer.close();
    }

    private void listVMInstances(BufferedWriter writer) throws Exception {
        boolean instancesNotFoundForInstanceGroup = true; //if it remains "true" it means it didn't found the instance Group
        String instanceGroupName = db.getInstanceGroupName();
        String zone = db.getZone();
        try (InstancesClient client = InstancesClient.create()) {
            for (Instance e : client.list(PROJECT_ID, zone).iterateAll()) {
                if (e.getStatus() == Instance.Status.RUNNING) {
                    String ip_address = e.getNetworkInterfaces(0).getAccessConfigs(0).getNatIP();
                    if (e.getName().contains(instanceGroupName)) {
                        writer.write(ip_address);
                        writer.write("\n");
                        instancesNotFoundForInstanceGroup = false;
                    }
                }
            }
        }
        if (instancesNotFoundForInstanceGroup) {
            writer.write("No VMs found for the zone " + zone + " on instance group " + instanceGroupName + " (Check if the instance group is initialized and with the correct zone)");
            throw new Exception();
        }
    }
}