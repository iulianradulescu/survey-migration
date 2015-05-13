/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.input.commands;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 *
 * @author iulian.radulescu
 */
@Component
public class UserCommandFactory {
    
    @Resource
    private Map<String,UserCommand> commands;
}
