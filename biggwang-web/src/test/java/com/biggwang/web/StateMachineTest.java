package com.biggwang.web;

import com.biggwang.web.code.EventsEnum;
import com.biggwang.web.code.StatesEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;

@SpringBootTest
public class StateMachineTest {

    @Autowired
    private StateMachine<StatesEnum, EventsEnum> machine;

    @Test
    void stateMachineTest() throws Exception {
        StateMachineTestPlan<StatesEnum, EventsEnum> stateMachineTestPlan =
                StateMachineTestPlanBuilder.<StatesEnum, EventsEnum>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(machine)
                        .step()
                        .sendEvent(EventsEnum.OPEN).expectStateEntered(StatesEnum.UNLOCKED)
                        .sendEvent(EventsEnum.CLOSE).expectStateEntered(StatesEnum.UNLOCKED)
                        .and()
                        .build();
        stateMachineTestPlan.test();
    }

}
