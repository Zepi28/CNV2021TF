// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: grpcContract.proto

package contract;

public final class GrpcContract {
  private GrpcContract() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_contract_RequestFiles_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_contract_RequestFiles_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_contract_ResultFiles_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_contract_ResultFiles_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_contract_Contents_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_contract_Contents_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_contract_ResultUpload_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_contract_ResultUpload_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022grpcContract.proto\022\010contract\"M\n\014Reques" +
      "tFiles\022\r\n\005date1\030\001 \001(\t\022\r\n\005date2\030\002 \001(\t\022\r\n\005" +
      "label\030\003 \001(\t\022\020\n\010download\030\004 \001(\010\"B\n\013ResultF" +
      "iles\022\023\n\013totalBlocks\030\001 \001(\005\022\020\n\010filename\030\002 " +
      "\001(\t\022\014\n\004data\030\003 \001(\014\"\242\001\n\010Contents\022\026\n\016fileBl" +
      "ockBytes\030\001 \001(\014\022\020\n\010filename\030\002 \001(\t\022\023\n\013cont" +
      "entType\030\003 \001(\t\022&\n\004flag\030\004 \001(\0162\030.contract.C" +
      "ontents.Flags\"/\n\005Flags\022\010\n\004NONE\020\000\022\r\n\tOVER" +
      "WRITE\020\001\022\r\n\tKEEP_BOTH\020\002\"/\n\014ResultUpload\022\020" +
      "\n\010filename\030\001 \001(\t\022\r\n\005label\030\002 \001(\t2\236\001\n\013File" +
      "Service\022H\n\025ReceiveFilesWithLabel\022\026.contr" +
      "act.RequestFiles\032\025.contract.ResultFiles0" +
      "\001\022E\n\023UploadFileToStorage\022\022.contract.Cont" +
      "ents\032\026.contract.ResultUpload(\0010\001B\014\n\010cont" +
      "ractP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_contract_RequestFiles_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_contract_RequestFiles_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_contract_RequestFiles_descriptor,
        new java.lang.String[] { "Date1", "Date2", "Label", "Download", });
    internal_static_contract_ResultFiles_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_contract_ResultFiles_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_contract_ResultFiles_descriptor,
        new java.lang.String[] { "TotalBlocks", "Filename", "Data", });
    internal_static_contract_Contents_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_contract_Contents_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_contract_Contents_descriptor,
        new java.lang.String[] { "FileBlockBytes", "Filename", "ContentType", "Flag", });
    internal_static_contract_ResultUpload_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_contract_ResultUpload_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_contract_ResultUpload_descriptor,
        new java.lang.String[] { "Filename", "Label", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}