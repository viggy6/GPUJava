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

@SpringBootApplication
public class ToolBaseMainBe1Application implements CommandLineRunner{

	@Autowired
	GPUService service;
	public static void main(String[] args) {
	
		SpringApplication.run(ToolBaseMainBe1Application.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		service.gpuCalc();
		//service.cpuMultiCalc();
		//service.cpuCalc();
		System.out.println(System.currentTimeMillis()-start);
	}

}
