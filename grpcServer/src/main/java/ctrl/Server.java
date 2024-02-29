package ctrl;

import contract.*;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class Server extends FileServiceGrpc.FileServiceImplBase {
    private static int svcPort;

    public static void main(String[] args) throws Exception {
        try {
            if (args.length != 1) {
                System.out.println("Usage [port]\n");
                System.exit(0);
            }
            try {
                svcPort = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.out.println("Not a valid port : " + args[0]);
                System.exit(0);
            }
            io.grpc.Server svc = ServerBuilder.forPort(svcPort).addService(new Server()).build();
            svc.start();
            System.out.println("Server started, listening on " + svcPort);
            svc.awaitTermination();
            svc.shutdown();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void receiveFilesWithLabel(RequestFiles request, StreamObserver<ResultFiles> responseObserver) {
        //ListFilesStream will write to stream with OnNext , OnCompleted ...
        new ListFilesStream(request, responseObserver);
    }

    @Override
    public StreamObserver<Contents> uploadFileToStorage(StreamObserver<ResultUpload> responseObserver) {
        try {
            return new ServerStreamObserver(responseObserver);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
