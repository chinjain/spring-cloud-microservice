package com.microservices.photoappuserservice.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.photoappuserservice.ui.model.AlbumResponseModel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "album-ws")
public interface AlbumServiceClient {

	@GetMapping("/users/{id}/albums")
	@CircuitBreaker(name = "album-ws", fallbackMethod = "getAlbumFallBack")
	public List<AlbumResponseModel> getAlbums(@PathVariable("id") String id);

	default List<AlbumResponseModel> getAlbumFallBack(String id, Throwable ex) {
		System.out.println("AlbumServiceClient.getAlbumFallBack()");
		System.err.println("Param value is :" + id);
		System.err.println("Exception took place: " + ex.getMessage());
		return new ArrayList<AlbumResponseModel>();
	}

}

//
//@Component
//class AlbumFallBack implements AlbumServiceClient {
//
//	@Override
//	public List<AlbumResponseModel> getAlbums(String id) {
//
//		return new ArrayList<AlbumResponseModel>();
//	}
//
//}
