package com.java.stcassessment.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.stcassessment.exception.ErrorCode;
import com.java.stcassessment.exception.NotFoundException;
import com.java.stcassessment.model.Item;
import com.java.stcassessment.repository.ItemRepository;

@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemRepository itemRepository;

    public Item getItem(Integer id) {
        logger.debug("ItemService>>getItem with id: {}", id);

        Optional<Item> optionalItem = itemRepository.findById(id);

        if (!optionalItem.isPresent()) {
            throw new NotFoundException(ErrorCode.NotFoundItem);
        }
        Item item = optionalItem.get();
        logger.debug("getItem>>item: {}", item);

        return item;
    }

    public Item save(Item item) {
        logger.debug("ItemService>>save Item : {}", item);

        item = itemRepository.save(item);
        return item;
    }


}
