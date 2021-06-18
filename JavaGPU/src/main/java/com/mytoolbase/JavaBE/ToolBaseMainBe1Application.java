package com.mytoolbase.JavaBE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aparapi.Kernel;
import com.aparapi.device.Device;
import com.aparapi.internal.kernel.KernelManager;
import com.aparapi.internal.kernel.KernelPreferences;
import com.mytoolbase.JavaBE.gpuService.GPUService;

//@SpringBootApplication
public class ToolBaseMainBe1Application {

	static GPUService service;

	public static void main(String[] args) throws InterruptedException {

		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
				service.size=20000;
				service.gpuCalc();
		// service.size=10000;
		// service.cpuMultiCalc();
//				service.size=1000;
//				service.cpuCalc();
		// service.fullScaledLoad();
	
		System.out.println(System.currentTimeMillis() - start);

	}

	

}
