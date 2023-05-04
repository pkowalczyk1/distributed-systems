package pl.agh.edu.grpcweb.gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.54.0)",
    comments = "Source: proto/web_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class WebServiceGrpc {

  private WebServiceGrpc() {}

  public static final String SERVICE_NAME = "webservice.WebService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.NumberType,
      pl.agh.edu.grpcweb.gen.NumberType> getFactorialMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Factorial",
      requestType = pl.agh.edu.grpcweb.gen.NumberType.class,
      responseType = pl.agh.edu.grpcweb.gen.NumberType.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.NumberType,
      pl.agh.edu.grpcweb.gen.NumberType> getFactorialMethod() {
    io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.NumberType, pl.agh.edu.grpcweb.gen.NumberType> getFactorialMethod;
    if ((getFactorialMethod = WebServiceGrpc.getFactorialMethod) == null) {
      synchronized (WebServiceGrpc.class) {
        if ((getFactorialMethod = WebServiceGrpc.getFactorialMethod) == null) {
          WebServiceGrpc.getFactorialMethod = getFactorialMethod =
              io.grpc.MethodDescriptor.<pl.agh.edu.grpcweb.gen.NumberType, pl.agh.edu.grpcweb.gen.NumberType>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Factorial"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.agh.edu.grpcweb.gen.NumberType.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.agh.edu.grpcweb.gen.NumberType.getDefaultInstance()))
              .setSchemaDescriptor(new WebServiceMethodDescriptorSupplier("Factorial"))
              .build();
        }
      }
    }
    return getFactorialMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.StringType,
      pl.agh.edu.grpcweb.gen.StringType> getToUpperCaseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ToUpperCase",
      requestType = pl.agh.edu.grpcweb.gen.StringType.class,
      responseType = pl.agh.edu.grpcweb.gen.StringType.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.StringType,
      pl.agh.edu.grpcweb.gen.StringType> getToUpperCaseMethod() {
    io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.StringType, pl.agh.edu.grpcweb.gen.StringType> getToUpperCaseMethod;
    if ((getToUpperCaseMethod = WebServiceGrpc.getToUpperCaseMethod) == null) {
      synchronized (WebServiceGrpc.class) {
        if ((getToUpperCaseMethod = WebServiceGrpc.getToUpperCaseMethod) == null) {
          WebServiceGrpc.getToUpperCaseMethod = getToUpperCaseMethod =
              io.grpc.MethodDescriptor.<pl.agh.edu.grpcweb.gen.StringType, pl.agh.edu.grpcweb.gen.StringType>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ToUpperCase"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.agh.edu.grpcweb.gen.StringType.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.agh.edu.grpcweb.gen.StringType.getDefaultInstance()))
              .setSchemaDescriptor(new WebServiceMethodDescriptorSupplier("ToUpperCase"))
              .build();
        }
      }
    }
    return getToUpperCaseMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.ListArgument,
      pl.agh.edu.grpcweb.gen.DoubleResult> getGetAverageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAverage",
      requestType = pl.agh.edu.grpcweb.gen.ListArgument.class,
      responseType = pl.agh.edu.grpcweb.gen.DoubleResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.ListArgument,
      pl.agh.edu.grpcweb.gen.DoubleResult> getGetAverageMethod() {
    io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.ListArgument, pl.agh.edu.grpcweb.gen.DoubleResult> getGetAverageMethod;
    if ((getGetAverageMethod = WebServiceGrpc.getGetAverageMethod) == null) {
      synchronized (WebServiceGrpc.class) {
        if ((getGetAverageMethod = WebServiceGrpc.getGetAverageMethod) == null) {
          WebServiceGrpc.getGetAverageMethod = getGetAverageMethod =
              io.grpc.MethodDescriptor.<pl.agh.edu.grpcweb.gen.ListArgument, pl.agh.edu.grpcweb.gen.DoubleResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAverage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.agh.edu.grpcweb.gen.ListArgument.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.agh.edu.grpcweb.gen.DoubleResult.getDefaultInstance()))
              .setSchemaDescriptor(new WebServiceMethodDescriptorSupplier("GetAverage"))
              .build();
        }
      }
    }
    return getGetAverageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.NumberType,
      pl.agh.edu.grpcweb.gen.NumberType> getGenerateRandomNumbersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateRandomNumbers",
      requestType = pl.agh.edu.grpcweb.gen.NumberType.class,
      responseType = pl.agh.edu.grpcweb.gen.NumberType.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.NumberType,
      pl.agh.edu.grpcweb.gen.NumberType> getGenerateRandomNumbersMethod() {
    io.grpc.MethodDescriptor<pl.agh.edu.grpcweb.gen.NumberType, pl.agh.edu.grpcweb.gen.NumberType> getGenerateRandomNumbersMethod;
    if ((getGenerateRandomNumbersMethod = WebServiceGrpc.getGenerateRandomNumbersMethod) == null) {
      synchronized (WebServiceGrpc.class) {
        if ((getGenerateRandomNumbersMethod = WebServiceGrpc.getGenerateRandomNumbersMethod) == null) {
          WebServiceGrpc.getGenerateRandomNumbersMethod = getGenerateRandomNumbersMethod =
              io.grpc.MethodDescriptor.<pl.agh.edu.grpcweb.gen.NumberType, pl.agh.edu.grpcweb.gen.NumberType>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GenerateRandomNumbers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.agh.edu.grpcweb.gen.NumberType.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pl.agh.edu.grpcweb.gen.NumberType.getDefaultInstance()))
              .setSchemaDescriptor(new WebServiceMethodDescriptorSupplier("GenerateRandomNumbers"))
              .build();
        }
      }
    }
    return getGenerateRandomNumbersMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WebServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WebServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WebServiceStub>() {
        @java.lang.Override
        public WebServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WebServiceStub(channel, callOptions);
        }
      };
    return WebServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WebServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WebServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WebServiceBlockingStub>() {
        @java.lang.Override
        public WebServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WebServiceBlockingStub(channel, callOptions);
        }
      };
    return WebServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WebServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WebServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WebServiceFutureStub>() {
        @java.lang.Override
        public WebServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WebServiceFutureStub(channel, callOptions);
        }
      };
    return WebServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void factorial(pl.agh.edu.grpcweb.gen.NumberType request,
        io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.NumberType> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFactorialMethod(), responseObserver);
    }

    /**
     */
    default void toUpperCase(pl.agh.edu.grpcweb.gen.StringType request,
        io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.StringType> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getToUpperCaseMethod(), responseObserver);
    }

    /**
     */
    default void getAverage(pl.agh.edu.grpcweb.gen.ListArgument request,
        io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.DoubleResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAverageMethod(), responseObserver);
    }

    /**
     */
    default void generateRandomNumbers(pl.agh.edu.grpcweb.gen.NumberType request,
        io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.NumberType> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGenerateRandomNumbersMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service WebService.
   */
  public static abstract class WebServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return WebServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service WebService.
   */
  public static final class WebServiceStub
      extends io.grpc.stub.AbstractAsyncStub<WebServiceStub> {
    private WebServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WebServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WebServiceStub(channel, callOptions);
    }

    /**
     */
    public void factorial(pl.agh.edu.grpcweb.gen.NumberType request,
        io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.NumberType> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFactorialMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void toUpperCase(pl.agh.edu.grpcweb.gen.StringType request,
        io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.StringType> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getToUpperCaseMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAverage(pl.agh.edu.grpcweb.gen.ListArgument request,
        io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.DoubleResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAverageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void generateRandomNumbers(pl.agh.edu.grpcweb.gen.NumberType request,
        io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.NumberType> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGenerateRandomNumbersMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service WebService.
   */
  public static final class WebServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<WebServiceBlockingStub> {
    private WebServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WebServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WebServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public pl.agh.edu.grpcweb.gen.NumberType factorial(pl.agh.edu.grpcweb.gen.NumberType request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFactorialMethod(), getCallOptions(), request);
    }

    /**
     */
    public pl.agh.edu.grpcweb.gen.StringType toUpperCase(pl.agh.edu.grpcweb.gen.StringType request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getToUpperCaseMethod(), getCallOptions(), request);
    }

    /**
     */
    public pl.agh.edu.grpcweb.gen.DoubleResult getAverage(pl.agh.edu.grpcweb.gen.ListArgument request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAverageMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<pl.agh.edu.grpcweb.gen.NumberType> generateRandomNumbers(
        pl.agh.edu.grpcweb.gen.NumberType request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGenerateRandomNumbersMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service WebService.
   */
  public static final class WebServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<WebServiceFutureStub> {
    private WebServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WebServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WebServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pl.agh.edu.grpcweb.gen.NumberType> factorial(
        pl.agh.edu.grpcweb.gen.NumberType request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFactorialMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pl.agh.edu.grpcweb.gen.StringType> toUpperCase(
        pl.agh.edu.grpcweb.gen.StringType request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getToUpperCaseMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pl.agh.edu.grpcweb.gen.DoubleResult> getAverage(
        pl.agh.edu.grpcweb.gen.ListArgument request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAverageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FACTORIAL = 0;
  private static final int METHODID_TO_UPPER_CASE = 1;
  private static final int METHODID_GET_AVERAGE = 2;
  private static final int METHODID_GENERATE_RANDOM_NUMBERS = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FACTORIAL:
          serviceImpl.factorial((pl.agh.edu.grpcweb.gen.NumberType) request,
              (io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.NumberType>) responseObserver);
          break;
        case METHODID_TO_UPPER_CASE:
          serviceImpl.toUpperCase((pl.agh.edu.grpcweb.gen.StringType) request,
              (io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.StringType>) responseObserver);
          break;
        case METHODID_GET_AVERAGE:
          serviceImpl.getAverage((pl.agh.edu.grpcweb.gen.ListArgument) request,
              (io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.DoubleResult>) responseObserver);
          break;
        case METHODID_GENERATE_RANDOM_NUMBERS:
          serviceImpl.generateRandomNumbers((pl.agh.edu.grpcweb.gen.NumberType) request,
              (io.grpc.stub.StreamObserver<pl.agh.edu.grpcweb.gen.NumberType>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getFactorialMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              pl.agh.edu.grpcweb.gen.NumberType,
              pl.agh.edu.grpcweb.gen.NumberType>(
                service, METHODID_FACTORIAL)))
        .addMethod(
          getToUpperCaseMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              pl.agh.edu.grpcweb.gen.StringType,
              pl.agh.edu.grpcweb.gen.StringType>(
                service, METHODID_TO_UPPER_CASE)))
        .addMethod(
          getGetAverageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              pl.agh.edu.grpcweb.gen.ListArgument,
              pl.agh.edu.grpcweb.gen.DoubleResult>(
                service, METHODID_GET_AVERAGE)))
        .addMethod(
          getGenerateRandomNumbersMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              pl.agh.edu.grpcweb.gen.NumberType,
              pl.agh.edu.grpcweb.gen.NumberType>(
                service, METHODID_GENERATE_RANDOM_NUMBERS)))
        .build();
  }

  private static abstract class WebServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WebServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pl.agh.edu.grpcweb.gen.WebServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WebService");
    }
  }

  private static final class WebServiceFileDescriptorSupplier
      extends WebServiceBaseDescriptorSupplier {
    WebServiceFileDescriptorSupplier() {}
  }

  private static final class WebServiceMethodDescriptorSupplier
      extends WebServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WebServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (WebServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WebServiceFileDescriptorSupplier())
              .addMethod(getFactorialMethod())
              .addMethod(getToUpperCaseMethod())
              .addMethod(getGetAverageMethod())
              .addMethod(getGenerateRandomNumbersMethod())
              .build();
        }
      }
    }
    return result;
  }
}
