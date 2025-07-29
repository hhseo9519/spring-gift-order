package gift.controller;
import gift.dto.LocalLoginRequestDto;
import gift.dto.LocalLoginResponseDto;
import gift.dto.LocalRegisterRequestDto;
import gift.dto.LocalRegisterResponseDto;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    public final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping("/register")
    public ResponseEntity<LocalRegisterResponseDto> registerMember(
            @RequestBody @Valid LocalRegisterRequestDto requestDto) {

        String token = memberService.register(requestDto);
        LocalRegisterResponseDto responseDto = new LocalRegisterResponseDto(token);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<LocalLoginResponseDto> loginMember(
            @RequestBody @Valid LocalLoginRequestDto requestDto) {

        String token = memberService.login(requestDto);
        LocalLoginResponseDto responseDto = new LocalLoginResponseDto(token);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }



}
