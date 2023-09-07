package kopo.poly.service.impl;

import kopo.poly.persistance.mapper.IUserMapper;
import kopo.poly.service.IUerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements IUerService {

    private final IUserMapper userMapper;

}
