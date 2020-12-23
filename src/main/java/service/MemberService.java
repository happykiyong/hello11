package service;

// 회원 서비스 개발 - 비즈니스 로직
//데이터 입력, 수정, 조회 등을 수행하는 루틴, 보이는 것의 그 뒤에서 일어나는 각종 처리

import Repository.MemberRepository;
import domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {

    // test 할 때, 여기서 생성된 객체와 실제 Memory 가 동일한 객체의 대상이 아니다. 따라서 실제 DB의 정보를
    // 비교하기 위해서는 Dependency Injection이 필요하다.
    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

    // 생성자를 통해 DI
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // 회원가입
    public Long join(Member member){
        //중복 회원 조회
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        memberRepository.findByName(member.getName())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // memberId와 동일한 계정 반환
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
