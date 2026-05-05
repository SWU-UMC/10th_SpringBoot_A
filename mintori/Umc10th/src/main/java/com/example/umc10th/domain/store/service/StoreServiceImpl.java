package com.example.umc10th.domain.store.service;

import com.example.umc10th.domain.store.converter.StoreConverter;
import com.example.umc10th.domain.store.dto.request.StoreRequest;
import com.example.umc10th.domain.store.dto.response.StorePageResponse;
import com.example.umc10th.domain.store.dto.response.StoreResponse;
import com.example.umc10th.domain.store.entity.Region;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.exception.StoreException;
import com.example.umc10th.domain.store.repository.RegionRepository;
import com.example.umc10th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public StoreResponse addStore(StoreRequest request) {
        Region region = regionRepository.findById(request.regionId())
                .orElseThrow(() -> new StoreException("존재하지 않는 지역입니다."));

        Store store = StoreConverter.toStore(request, region);
        Store saved = storeRepository.save(store);
        return StoreConverter.toStoreResponse(saved);
    }

    @Override
    public StorePageResponse getStoresByRegion(Long regionId, int page, int size) {
        if (!regionRepository.existsById(regionId)) {
            throw new StoreException("존재하지 않는 지역입니다.");
        }
        Page<Store> result = storeRepository.findByRegionId(
                regionId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        return StoreConverter.toStorePageResponse(result);
    }
}
