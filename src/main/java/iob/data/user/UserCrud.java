package iob.data.user;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserCrud extends PagingAndSortingRepository<UserEntity, String>{

}
