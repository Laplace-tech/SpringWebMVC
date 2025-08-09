package hello.springmvc2.domain.item.service;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import hello.springmvc2.domain.item.controller.form.ItemSaveForm;
import hello.springmvc2.domain.item.controller.form.ItemUpdateForm;
import hello.springmvc2.domain.item.controller.mapper.ItemMapper;
import hello.springmvc2.domain.item.entry.Item;
import hello.springmvc2.domain.item.exception.ItemNotFoundException;
import hello.springmvc2.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;
	private static final int MIN_TOTAL_PRICE = 10_000;
	
	public Item saveItem(ItemSaveForm form) {
		Item item = ItemMapper.toEntity(form);
		return itemRepository.save(item);
	}

	public Item findItemById(Long id) {
		return itemRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("아이템이 존재하지 않습니다. id : " + id));
	}

	public List<Item> findAllItems() {
		return itemRepository.findAll();
	}

	public void updateItem(Long id, ItemUpdateForm form) {
		validateIdMatch(id, form::getId);
		Item updatedItem = ItemMapper.toEntity(form);
		boolean updated = itemRepository.update(updatedItem);
		if(!updated) {
			throw new ItemNotFoundException("수정할 아이템이 존재하지 않습니다. id : " + id);
		}
	}

	public void deleteItem(Long id) {
		boolean deleted = itemRepository.delete(id);
		if(!deleted) {
			throw new ItemNotFoundException("삭제할 아이템이 존재하지 않습니다. id : " + id);
		}
	}
	
	private void validateIdMatch(Long pathId, Supplier<Long> formIdSupplier) {
		if(!pathId.equals(formIdSupplier.get())) {
            throw new IllegalArgumentException("Path variable id와 form의 id가 일치하지 않습니다. pathId: " + pathId + ", formId: " + formIdSupplier.get());
		}
	}
	
	public void validateTotalPrice(Object form, BindingResult bindingResult) {
		Integer price = null;
		Integer quantity = null;
		
		if(form instanceof ItemSaveForm saveForm) {
			price = saveForm.getPrice();
			quantity = saveForm.getQuantity();
		} else if (form instanceof ItemUpdateForm updateForm) {
			price = updateForm.getPrice();
			quantity = updateForm.getQuantity();
		} else {
            log.warn("지원하지 않는 폼 타입으로 검증 시도: {}", form.getClass());
            return;
        }
		
		if(price != null && quantity != null) {
			int totalPrice = price * quantity;
			if(totalPrice < MIN_TOTAL_PRICE) {
				// 글로벌 오류 등록
				bindingResult.reject("totalPriceMin", 
						new Object[] {MIN_TOTAL_PRICE, totalPrice}, 
						String.format("총 가격은 최소 %d원 이상이어야 한다. 현재 : %d", MIN_TOTAL_PRICE, totalPrice));
			}
		}
	}
	
}