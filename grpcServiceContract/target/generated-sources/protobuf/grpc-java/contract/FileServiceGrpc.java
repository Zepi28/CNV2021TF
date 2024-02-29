package contract;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.28.1)",
    comments = "Source: grpcContract.proto")
public final class FileServiceGrpc {

  private FileServiceGrpc() {}

  public static final String SERVICE_NAME = "contract.FileService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<contract.RequestFiles,
      contract.ResultFiles> getReceiveFilesWithLabelMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReceiveFilesWithLabel",
      requestType = contract.RequestFiles.class,
      responseType = contract.ResultFiles.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<contract.RequestFiles,
      contract.ResultFiles> getReceiveFilesWithLabelMethod() {
    io.grpc.MethodDescriptor<contract.RequestFiles, contract.ResultFiles> getReceiveFilesWithLabelMethod;
    if ((getReceiveFilesWithLabelMethod = FileServiceGrpc.getReceiveFilesWithLabelMethod) == null) {
      synchronized (FileServiceGrpc.class) {
        if ((getReceiveFilesWithLabelMethod = FileServiceGrpc.getReceiveFilesWithLabelMethod) == null) {
          FileServiceGrpc.getReceiveFilesWithLabelMethod = getReceiveFilesWithLabelMethod =
              io.grpc.MethodDescriptor.<contract.RequestFiles, contract.ResultFiles>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReceiveFilesWithLabel"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contract.RequestFiles.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contract.ResultFiles.getDefaultInstance()))
              .setSchemaDescriptor(new FileServiceMethodDescriptorSupplier("ReceiveFilesWithLabel"))
              .build();
        }
      }
    }
    return getReceiveFilesWithLabelMethod;
  }

  private static volatile io.grpc.MethodDescriptor<contract.Contents,
      contract.ResultUpload> getUploadFileToStorageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadFileToStorage",
      requestType = contract.Contents.class,
      responseType = contract.ResultUpload.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<contract.Contents,
      contract.ResultUpload> getUploadFileToStorageMethod() {
    io.grpc.MethodDescriptor<contract.Contents, contract.ResultUpload> getUploadFileToStorageMethod;
    if ((getUploadFileToStorageMethod = FileServiceGrpc.getUploadFileToStorageMethod) == null) {
      synchronized (FileServiceGrpc.class) {
        if ((getUploadFileToStorageMethod = FileServiceGrpc.getUploadFileToStorageMethod) == null) {
          FileServiceGrpc.getUploadFileToStorageMethod = getUploadFileToStorageMethod =
              io.grpc.MethodDescriptor.<contract.Contents, contract.ResultUpload>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UploadFileToStorage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contract.Contents.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contract.ResultUpload.getDefaultInstance()))
              .setSchemaDescriptor(new FileServiceMethodDescriptorSupplier("UploadFileToStorage"))
              .build();
        }
      }
    }
    return getUploadFileToStorageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FileServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileServiceStub>() {
        @java.lang.Override
        public FileServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileServiceStub(channel, callOptions);
        }
      };
    return FileServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FileServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileServiceBlockingStub>() {
        @java.lang.Override
        public FileServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileServiceBlockingStub(channel, callOptions);
        }
      };
    return FileServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FileServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileServiceFutureStub>() {
        @java.lang.Override
        public FileServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileServiceFutureStub(channel, callOptions);
        }
      };
    return FileServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class FileServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void receiveFilesWithLabel(contract.RequestFiles request,
        io.grpc.stub.StreamObserver<contract.ResultFiles> responseObserver) {
      asyncUnimplementedUnaryCall(getReceiveFilesWithLabelMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<contract.Contents> uploadFileToStorage(
        io.grpc.stub.StreamObserver<contract.ResultUpload> responseObserver) {
      return asyncUnimplementedStreamingCall(getUploadFileToStorageMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getReceiveFilesWithLabelMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                contract.RequestFiles,
                contract.ResultFiles>(
                  this, METHODID_RECEIVE_FILES_WITH_LABEL)))
          .addMethod(
            getUploadFileToStorageMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                contract.Contents,
                contract.ResultUpload>(
                  this, METHODID_UPLOAD_FILE_TO_STORAGE)))
          .build();
    }
  }

  /**
   */
  public static final class FileServiceStub extends io.grpc.stub.AbstractAsyncStub<FileServiceStub> {
    private FileServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileServiceStub(channel, callOptions);
    }

    /**
     */
    public void receiveFilesWithLabel(contract.RequestFiles request,
        io.grpc.stub.StreamObserver<contract.ResultFiles> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getReceiveFilesWithLabelMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<contract.Contents> uploadFileToStorage(
        io.grpc.stub.StreamObserver<contract.ResultUpload> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getUploadFileToStorageMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class FileServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<FileServiceBlockingStub> {
    private FileServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<contract.ResultFiles> receiveFilesWithLabel(
        contract.RequestFiles request) {
      return blockingServerStreamingCall(
          getChannel(), getReceiveFilesWithLabelMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FileServiceFutureStub extends io.grpc.stub.AbstractFutureStub<FileServiceFutureStub> {
    private FileServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_RECEIVE_FILES_WITH_LABEL = 0;
  private static final int METHODID_UPLOAD_FILE_TO_STORAGE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FileServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FileServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RECEIVE_FILES_WITH_LABEL:
          serviceImpl.receiveFilesWithLabel((contract.RequestFiles) request,
              (io.grpc.stub.StreamObserver<contract.ResultFiles>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD_FILE_TO_STORAGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadFileToStorage(
              (io.grpc.stub.StreamObserver<contract.ResultUpload>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class FileServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FileServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return contract.GrpcContract.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FileService");
    }
  }

  private static final class FileServiceFileDescriptorSupplier
      extends FileServiceBaseDescriptorSupplier {
    FileServiceFileDescriptorSupplier() {}
  }

  private static final class FileServiceMethodDescriptorSupplier
      extends FileServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FileServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FileServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FileServiceFileDescriptorSupplier())
              .addMethod(getReceiveFilesWithLabelMethod())
              .addMethod(getUploadFileToStorageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
