package de.kaiserv.ohtello.gyror;

import com.pi4j.component.gyroscope.honeywell.HMC5883L;
import com.pi4j.io.i2c.I2CFactory;

public class Main1 {

    public static void main(String... args) {
        System.out.println("Running HMC5883L");
        try {
            HMC5883L hmc5883l = new HMC5883L(I2CFactory.getInstance(0));
            hmc5883l.enable();
            //hmc5883l.recalibrateOffset();
            while (true) {
                hmc5883l.readGyro();
                System.out.printf("X=%d ,Y=%d, Z=%d%n", hmc5883l.X.getRawValue(), hmc5883l.Y.getRawValue(), hmc5883l.Z.getRawValue());
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
