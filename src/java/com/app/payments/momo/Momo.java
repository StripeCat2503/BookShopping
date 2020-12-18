/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.payments.momo;

import com.app.constants.MomoPartnerInfo;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;

/**
 *
 * @author DuyNK
 */
public class Momo {
    public static Environment env;
    
    public static void init(){
        if(env == null){
            env = Environment.selectEnv("dev", Environment.ProcessType.PAY_GATE);
            env.setPartnerInfo(new PartnerInfo(MomoPartnerInfo.PARTNER_CODE, MomoPartnerInfo.ACCESS_KEY, MomoPartnerInfo.SECRECT_KEY));
        }
    }
}
