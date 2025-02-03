package guru.qa.country.service;

import com.google.protobuf.Empty;
import guru.qa.country.data.Country;
import guru.qa.country.data.CountryEntity;
import guru.qa.grpc.country.*;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class GrpcCountryService extends CountryServiceGrpc.CountryServiceImplBase {

    private final CountryService countryService;

    public GrpcCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void country(IdRequest request, StreamObserver<CountryResponse> responseObserver) {
        final Country country = countryService.countryById(request.getId());
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(country.id().toString())
                        .setCountryName(country.countryName())
                        .setCountryCode(country.countryCode())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CountryRequest> addCountry(StreamObserver<CountryCountResponse> responseObserver) {
        final AtomicInteger idCounter = new AtomicInteger(0);
        return new StreamObserver<>() {
            @Override
            public void onNext(CountryRequest countryRequest) {
                idCounter.incrementAndGet();
                CountryEntity ce = new CountryEntity();
                ce.setCountryName(countryRequest.getCountryName());
                ce.setCountryCode(countryRequest.getCountryCode());
                countryService.addCountry(Country.fromEntity(ce));
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                CountryCountResponse response = CountryCountResponse.newBuilder()
                        .setCount(idCounter.get())
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void allCountries(Empty request, StreamObserver<CountriesResponse> responseObserver) {
        responseObserver.onNext(CountriesResponse.newBuilder()
                .addAllAllCountries(countryService.allCountries()
                        .stream()
                        .map(this::fromCountry).toList()).build()
        );
        responseObserver.onCompleted();
    }

    private CountryResponse fromCountry(Country country) {
        return CountryResponse.newBuilder()
                .setId(country.id().toString())
                .setCountryName(country.countryName())
                .setCountryCode(country.countryCode())
                .build();
    }
}
