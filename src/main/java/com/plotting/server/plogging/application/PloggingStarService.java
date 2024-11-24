//package com.plotting.server.plogging.application;
//
//import com.plotting.server.plogging.domain.Plogging;
//import com.plotting.server.plogging.domain.PloggingStar;
//import com.plotting.server.plogging.dto.response.MyPloggingStarListResponse;
//import com.plotting.server.plogging.dto.response.PloggingResponse;
//import com.plotting.server.plogging.dto.response.PloggingStarResponse;
//import com.plotting.server.plogging.repository.PloggingStarRepository;
//import com.plotting.server.plogging.repository.PloggingUserRepository;
//import com.plotting.server.user.application.UserService;
//import com.plotting.server.user.domain.User;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class PloggingStarService {
//    private final PloggingStarRepository ploggingStarRepository;
//    private final UserService userService;
//    private final PloggingService ploggingService;
//    private final PloggingUserRepository ploggingUserRepository;
//
//    public MyPloggingStarListResponse getMyPloggingStarList(Long userId){
//
//        return MyPloggingStarListResponse.from(ploggingStarRepository.findPloggingByUserId(userId).stream()
//                        .map(ploggingStar -> PloggingStarResponse.of(ploggingStar.getPlogging(), ploggingUserRepository.countActivePloggingUsersByPloggingId(ploggingStar.getPlogging().getId()))
//                        .toList()));
////                .map(plogging -> PloggingStarResponse.of(plogging, ploggingUserRepository.countActivePloggingUsersByPloggingId(plogging.getId()))
////                .toList());
//    }
//
//    @Transactional
//    public void updatePloggingStar(Long userId, Long ploggingId){
//        Optional<PloggingStar> ploggingStar = ploggingStarRepository.findByUserIdAndPloggingId(userId, ploggingId);
//
//        if(ploggingStar.isPresent()){
//            log.info("Deleting ploggingStar: {}", ploggingStar);
//            ploggingStarRepository.delete(ploggingStar.get());
//        }else{
//            Plogging plogging = ploggingService.getPlogging(ploggingId);
//            User user = userService.getUser(userId);
//
//            ploggingStarRepository.save(PloggingStar.of(user, plogging));
//        }
//    }
//}
