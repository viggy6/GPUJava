package com.mytoolbase.JavaBE.gpuService;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.aparapi.Kernel;

import com.aparapi.Range;
import com.aparapi.device.Device;
import com.aparapi.device.Device.TYPE;
import com.aparapi.device.OpenCLDevice;


public class GPUService {

	public static int size = 5000;

	public static void gpuCalc() {

		final float[] a = new float[size];
		final float[] b = new float[size];
	

		/* fill the arrays with random values */
		for (int i = 0; i < size; i++) {
			a[i] = (float) (Math.random() * 10000);
			b[i] = (float) (Math.random() * 10000);
		}
		final float[] sum = new float[size];

		Kernel kernel = new Kernel() {
			
			
			@Override
			public void run() {
				long s = 1l;
				for (int i = 0; i < sum.length; i++) {
					for (int j = 0; j < sum.length; j++) {
						s += i*j;
						
					}
					
					s += sum[i];
					
				}
				
				int gid = getGlobalId();
				
				sum[gid] = a[gid] * b[gid]*s;
				
				// Object o= new Object();
				
			}
		};
		
		kernel.execute(Range.create(size));
		
		kernel.dispose();

	}

	public static void cpuMultiCalc() throws InterruptedException {

		final float[] a = new float[size];
		final float[] b = new float[size];

		/* fill the arrays with random values */
		for (int i = 0; i < size; i++) {
			a[i] = (float) (Math.random() * 10000);
			b[i] = (float) (Math.random() * 10000);
		}
		final float[] sum = new float[size];
		IntStream.range(0, size).parallel().forEach((e) -> {
			long s = 1l;
			for (int i = 0; i < sum.length; i++) {
				for (int j = 0; j < sum.length; j++) {
					s += i*j;	
				}
			s += sum[i];
			}
			sum[e] = a[e] * b[e]*s;
		});

	}

	public static void cpuCalc() {

		final float[] a = new float[size];
		final float[] b = new float[size];

		/* fill the arrays with random values */
		for (int i = 0; i < size; i++) {
			a[i] = (float) (Math.random() * 10000);
			b[i] = (float) (Math.random() * 10000);
		}
		final float[] sum = new float[size];
		for (int i = 0; i < size; i++) {
			long s = 1l;
			for (int j = 0; j < sum.length; j++) {
				for (int k = 0; k < sum.length; k++) {
					s += k*j;	
				}
			s += sum[i];
			}
			sum[i] = a[i] * b[i]*s;

		}

	}

	public static void fullScaledLoad() throws InterruptedException, ExecutionException {
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
		
		Future<?> t1 = exec.submit(()->{fullCPU();});
		Future<?> t2 = exec.submit(()->{fullCPU();});
		Future<?> t3 = exec.submit(()->{fullCPU();});
		Future<?> t4 = exec.submit(()->{fullGPU();});
		t1.get();
		t2.get();
		t3.get();
		t4.get();
		exec.shutdown();
	}
	
	public static void fullGPU() {
		int x=1400000;
		int[] store= new int[x];
		System.out.println("GPU");
		Kernel k = new Kernel() {
			
			@Override
			public void run() {
				int id=getGlobalId();
				for(int i=1;i<x;i++) {
					if(id%i!=0) {
						store[i]=i;
					}
				}
			}
			
			
		};
		k.execute(Range.create(x));
		k.dispose();
	}
	
	public static void fullCPU() {
		int x=100000;
		System.out.println("CPU");
		for(int i=1;i<x;i++) {
			for(int j=1;j<x;j++) {
				if(i%j!=0) {
					
				}
			}
		}
	}
	
	public static void fullItr() {
		long s=0;
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				for(int k=0;k<size;k++) {
					s+=i*j*k;
				}
			}
		}
	}
	
	
	
}
