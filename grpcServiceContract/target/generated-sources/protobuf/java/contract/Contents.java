// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: grpcContract.proto

package contract;

/**
 * Protobuf type {@code contract.Contents}
 */
public  final class Contents extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:contract.Contents)
    ContentsOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Contents.newBuilder() to construct.
  private Contents(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Contents() {
    fileBlockBytes_ = com.google.protobuf.ByteString.EMPTY;
    filename_ = "";
    contentType_ = "";
    flag_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Contents();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Contents(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {

            fileBlockBytes_ = input.readBytes();
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            filename_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            contentType_ = s;
            break;
          }
          case 32: {
            int rawValue = input.readEnum();

            flag_ = rawValue;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return contract.GrpcContract.internal_static_contract_Contents_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return contract.GrpcContract.internal_static_contract_Contents_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            contract.Contents.class, contract.Contents.Builder.class);
  }

  /**
   * Protobuf enum {@code contract.Contents.Flags}
   */
  public enum Flags
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>NONE = 0;</code>
     */
    NONE(0),
    /**
     * <code>OVERWRITE = 1;</code>
     */
    OVERWRITE(1),
    /**
     * <code>KEEP_BOTH = 2;</code>
     */
    KEEP_BOTH(2),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>NONE = 0;</code>
     */
    public static final int NONE_VALUE = 0;
    /**
     * <code>OVERWRITE = 1;</code>
     */
    public static final int OVERWRITE_VALUE = 1;
    /**
     * <code>KEEP_BOTH = 2;</code>
     */
    public static final int KEEP_BOTH_VALUE = 2;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static Flags valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static Flags forNumber(int value) {
      switch (value) {
        case 0: return NONE;
        case 1: return OVERWRITE;
        case 2: return KEEP_BOTH;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<Flags>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        Flags> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<Flags>() {
            public Flags findValueByNumber(int number) {
              return Flags.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return contract.Contents.getDescriptor().getEnumTypes().get(0);
    }

    private static final Flags[] VALUES = values();

    public static Flags valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private Flags(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:contract.Contents.Flags)
  }

  public static final int FILEBLOCKBYTES_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString fileBlockBytes_;
  /**
   * <code>bytes fileBlockBytes = 1;</code>
   * @return The fileBlockBytes.
   */
  public com.google.protobuf.ByteString getFileBlockBytes() {
    return fileBlockBytes_;
  }

  public static final int FILENAME_FIELD_NUMBER = 2;
  private volatile java.lang.Object filename_;
  /**
   * <code>string filename = 2;</code>
   * @return The filename.
   */
  public java.lang.String getFilename() {
    java.lang.Object ref = filename_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      filename_ = s;
      return s;
    }
  }
  /**
   * <code>string filename = 2;</code>
   * @return The bytes for filename.
   */
  public com.google.protobuf.ByteString
      getFilenameBytes() {
    java.lang.Object ref = filename_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      filename_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CONTENTTYPE_FIELD_NUMBER = 3;
  private volatile java.lang.Object contentType_;
  /**
   * <code>string contentType = 3;</code>
   * @return The contentType.
   */
  public java.lang.String getContentType() {
    java.lang.Object ref = contentType_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      contentType_ = s;
      return s;
    }
  }
  /**
   * <code>string contentType = 3;</code>
   * @return The bytes for contentType.
   */
  public com.google.protobuf.ByteString
      getContentTypeBytes() {
    java.lang.Object ref = contentType_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      contentType_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int FLAG_FIELD_NUMBER = 4;
  private int flag_;
  /**
   * <code>.contract.Contents.Flags flag = 4;</code>
   * @return The enum numeric value on the wire for flag.
   */
  public int getFlagValue() {
    return flag_;
  }
  /**
   * <code>.contract.Contents.Flags flag = 4;</code>
   * @return The flag.
   */
  public contract.Contents.Flags getFlag() {
    @SuppressWarnings("deprecation")
    contract.Contents.Flags result = contract.Contents.Flags.valueOf(flag_);
    return result == null ? contract.Contents.Flags.UNRECOGNIZED : result;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!fileBlockBytes_.isEmpty()) {
      output.writeBytes(1, fileBlockBytes_);
    }
    if (!getFilenameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, filename_);
    }
    if (!getContentTypeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, contentType_);
    }
    if (flag_ != contract.Contents.Flags.NONE.getNumber()) {
      output.writeEnum(4, flag_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!fileBlockBytes_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, fileBlockBytes_);
    }
    if (!getFilenameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, filename_);
    }
    if (!getContentTypeBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, contentType_);
    }
    if (flag_ != contract.Contents.Flags.NONE.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(4, flag_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof contract.Contents)) {
      return super.equals(obj);
    }
    contract.Contents other = (contract.Contents) obj;

    if (!getFileBlockBytes()
        .equals(other.getFileBlockBytes())) return false;
    if (!getFilename()
        .equals(other.getFilename())) return false;
    if (!getContentType()
        .equals(other.getContentType())) return false;
    if (flag_ != other.flag_) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + FILEBLOCKBYTES_FIELD_NUMBER;
    hash = (53 * hash) + getFileBlockBytes().hashCode();
    hash = (37 * hash) + FILENAME_FIELD_NUMBER;
    hash = (53 * hash) + getFilename().hashCode();
    hash = (37 * hash) + CONTENTTYPE_FIELD_NUMBER;
    hash = (53 * hash) + getContentType().hashCode();
    hash = (37 * hash) + FLAG_FIELD_NUMBER;
    hash = (53 * hash) + flag_;
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static contract.Contents parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static contract.Contents parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static contract.Contents parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static contract.Contents parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static contract.Contents parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static contract.Contents parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static contract.Contents parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static contract.Contents parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static contract.Contents parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static contract.Contents parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static contract.Contents parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static contract.Contents parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(contract.Contents prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code contract.Contents}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:contract.Contents)
      contract.ContentsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return contract.GrpcContract.internal_static_contract_Contents_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return contract.GrpcContract.internal_static_contract_Contents_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              contract.Contents.class, contract.Contents.Builder.class);
    }

    // Construct using contract.Contents.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      fileBlockBytes_ = com.google.protobuf.ByteString.EMPTY;

      filename_ = "";

      contentType_ = "";

      flag_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return contract.GrpcContract.internal_static_contract_Contents_descriptor;
    }

    @java.lang.Override
    public contract.Contents getDefaultInstanceForType() {
      return contract.Contents.getDefaultInstance();
    }

    @java.lang.Override
    public contract.Contents build() {
      contract.Contents result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public contract.Contents buildPartial() {
      contract.Contents result = new contract.Contents(this);
      result.fileBlockBytes_ = fileBlockBytes_;
      result.filename_ = filename_;
      result.contentType_ = contentType_;
      result.flag_ = flag_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof contract.Contents) {
        return mergeFrom((contract.Contents)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(contract.Contents other) {
      if (other == contract.Contents.getDefaultInstance()) return this;
      if (other.getFileBlockBytes() != com.google.protobuf.ByteString.EMPTY) {
        setFileBlockBytes(other.getFileBlockBytes());
      }
      if (!other.getFilename().isEmpty()) {
        filename_ = other.filename_;
        onChanged();
      }
      if (!other.getContentType().isEmpty()) {
        contentType_ = other.contentType_;
        onChanged();
      }
      if (other.flag_ != 0) {
        setFlagValue(other.getFlagValue());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      contract.Contents parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (contract.Contents) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.google.protobuf.ByteString fileBlockBytes_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes fileBlockBytes = 1;</code>
     * @return The fileBlockBytes.
     */
    public com.google.protobuf.ByteString getFileBlockBytes() {
      return fileBlockBytes_;
    }
    /**
     * <code>bytes fileBlockBytes = 1;</code>
     * @param value The fileBlockBytes to set.
     * @return This builder for chaining.
     */
    public Builder setFileBlockBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      fileBlockBytes_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes fileBlockBytes = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearFileBlockBytes() {
      
      fileBlockBytes_ = getDefaultInstance().getFileBlockBytes();
      onChanged();
      return this;
    }

    private java.lang.Object filename_ = "";
    /**
     * <code>string filename = 2;</code>
     * @return The filename.
     */
    public java.lang.String getFilename() {
      java.lang.Object ref = filename_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        filename_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string filename = 2;</code>
     * @return The bytes for filename.
     */
    public com.google.protobuf.ByteString
        getFilenameBytes() {
      java.lang.Object ref = filename_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        filename_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string filename = 2;</code>
     * @param value The filename to set.
     * @return This builder for chaining.
     */
    public Builder setFilename(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      filename_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string filename = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearFilename() {
      
      filename_ = getDefaultInstance().getFilename();
      onChanged();
      return this;
    }
    /**
     * <code>string filename = 2;</code>
     * @param value The bytes for filename to set.
     * @return This builder for chaining.
     */
    public Builder setFilenameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      filename_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object contentType_ = "";
    /**
     * <code>string contentType = 3;</code>
     * @return The contentType.
     */
    public java.lang.String getContentType() {
      java.lang.Object ref = contentType_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        contentType_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string contentType = 3;</code>
     * @return The bytes for contentType.
     */
    public com.google.protobuf.ByteString
        getContentTypeBytes() {
      java.lang.Object ref = contentType_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        contentType_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string contentType = 3;</code>
     * @param value The contentType to set.
     * @return This builder for chaining.
     */
    public Builder setContentType(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      contentType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string contentType = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearContentType() {
      
      contentType_ = getDefaultInstance().getContentType();
      onChanged();
      return this;
    }
    /**
     * <code>string contentType = 3;</code>
     * @param value The bytes for contentType to set.
     * @return This builder for chaining.
     */
    public Builder setContentTypeBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      contentType_ = value;
      onChanged();
      return this;
    }

    private int flag_ = 0;
    /**
     * <code>.contract.Contents.Flags flag = 4;</code>
     * @return The enum numeric value on the wire for flag.
     */
    public int getFlagValue() {
      return flag_;
    }
    /**
     * <code>.contract.Contents.Flags flag = 4;</code>
     * @param value The enum numeric value on the wire for flag to set.
     * @return This builder for chaining.
     */
    public Builder setFlagValue(int value) {
      flag_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.contract.Contents.Flags flag = 4;</code>
     * @return The flag.
     */
    public contract.Contents.Flags getFlag() {
      @SuppressWarnings("deprecation")
      contract.Contents.Flags result = contract.Contents.Flags.valueOf(flag_);
      return result == null ? contract.Contents.Flags.UNRECOGNIZED : result;
    }
    /**
     * <code>.contract.Contents.Flags flag = 4;</code>
     * @param value The flag to set.
     * @return This builder for chaining.
     */
    public Builder setFlag(contract.Contents.Flags value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      flag_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.contract.Contents.Flags flag = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearFlag() {
      
      flag_ = 0;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:contract.Contents)
  }

  // @@protoc_insertion_point(class_scope:contract.Contents)
  private static final contract.Contents DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new contract.Contents();
  }

  public static contract.Contents getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Contents>
      PARSER = new com.google.protobuf.AbstractParser<Contents>() {
    @java.lang.Override
    public Contents parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Contents(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Contents> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Contents> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public contract.Contents getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

