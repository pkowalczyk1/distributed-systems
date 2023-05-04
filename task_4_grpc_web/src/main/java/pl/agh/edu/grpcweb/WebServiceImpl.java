package pl.agh.edu.grpcweb;

import io.grpc.stub.StreamObserver;
import pl.agh.edu.grpcweb.gen.DoubleResult;
import pl.agh.edu.grpcweb.gen.ListArgument;
import pl.agh.edu.grpcweb.gen.NumberType;
import pl.agh.edu.grpcweb.gen.StringType;
import pl.agh.edu.grpcweb.gen.WebServiceGrpc;

import java.util.List;
import java.util.Random;

public class WebServiceImpl extends WebServiceGrpc.WebServiceImplBase {

    @Override
    public void factorial(NumberType request, StreamObserver<NumberType> responseObserver) {
        System.out.println("Calculating factorial");
        long result = 1;
        for (int i = 1; i <= request.getArg(); i++) {
            result *= i;
        }

        NumberType wrappedResult = NumberType.newBuilder()
                .setArg(result)
                .build();
        responseObserver.onNext(wrappedResult);
        responseObserver.onCompleted();
    }

    @Override
    public void toUpperCase(StringType request, StreamObserver<StringType> responseObserver) {
        System.out.println("Converting to uppercase");
        StringType wrapperResult = StringType.newBuilder()
                .setValue(request.getValue().toUpperCase())
                .build();
        responseObserver.onNext(wrapperResult);
        responseObserver.onCompleted();
    }

    @Override
    public void getAverage(ListArgument request, StreamObserver<DoubleResult> responseObserver) {
        System.out.println("Calculating average");
        List<Integer> values = request.getValuesList();
        double avg = values.stream()
                .mapToDouble(el -> el)
                .average()
                .orElse(0.0);
        DoubleResult wrapperResult = DoubleResult.newBuilder()
                .setValue(avg)
                .build();
        responseObserver.onNext(wrapperResult);
        responseObserver.onCompleted();
    }

    @Override
    public void generateRandomNumbers(NumberType request, StreamObserver<NumberType> responseObserver) {
        System.out.println("Generating random numbers");
        Random random = new Random();
        for (int i = 0; i < request.getArg(); i++) {
            NumberType wrappedResult = NumberType.newBuilder()
                    .setArg(random.nextLong(100))
                    .build();
            responseObserver.onNext(wrappedResult);
            try {
                Thread.sleep(random.nextInt(5_000) + 1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        responseObserver.onCompleted();
    }
}
