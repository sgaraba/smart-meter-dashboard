package io.spring.demo.meter.dashboard.generator;

import io.spring.demo.meter.dashboard.DashboardProperties;
import reactor.core.publisher.Flux;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MeasuresCollector {

	private final Flux<ElectricityMeasure> electricityMeasures;

	public MeasuresCollector(DashboardProperties properties, WebClient.Builder webClientBuilder) {
		WebClient webClient = webClientBuilder
				.baseUrl(properties.getGenerator().getServiceUrl()).build();

		this.electricityMeasures = webClient
				.get().uri("/electricity/firehose")
				.retrieve().bodyToFlux(ElectricityMeasure.class)
				.share();
	}

	public Flux<ElectricityMeasure> getElectricityMeasures() {
		return this.electricityMeasures;
	}

}
