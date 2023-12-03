package zdorovo.tochka.repository;

import org.springframework.data.repository.CrudRepository;
import zdorovo.tochka.entity.Member;

public interface MemberRepository extends CrudRepository<Member, Long> {

    Member findByChatId(Long chatId);

}
