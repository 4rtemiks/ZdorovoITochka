package zdorovo.tochka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zdorovo.tochka.entity.Member;
import zdorovo.tochka.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findOne(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member findByChatId(Long chatId) {
        return memberRepository.findByChatId(chatId);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

}
