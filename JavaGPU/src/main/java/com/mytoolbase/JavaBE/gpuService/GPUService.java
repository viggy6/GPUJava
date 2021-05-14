package com.mytoolbase.JavaBE.gpuService;

import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.aparapi.Kernel;

import com.aparapi.Range;

@Service
public class GPUService {

	static int  size=100000000;
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
				long s = 0l;
				for (int i = 0; i < sum.length; i++) {
					s += sum[i];
				}
			
				int gid = getGlobalId();

				sum[gid] = a[gid] * b[gid];
				

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
		IntStream.range(0, size).parallel().forEach((e)->{
			long s =0l;
			for(int i=0;i<sum.length;i++) {
				s+=sum[i];
			}
			sum[e]=a[e]*b[e];
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
			long s=0l;
			for (int j = 0; j < sum.length; j++) {
				s+=sum[j];
			}

			sum[i] = a[i] * b[i];

		}
		System.out.println(sum.length);
	}
}


