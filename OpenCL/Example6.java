package class6;


import org.jocl.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.bridj.Pointer.allocateFloats;
import static org.jocl.CL.*;
import static org.jocl.CL.clGetDeviceInfo;
import static org.jocl.CL.clGetPlatformInfo;

/**
 * Created with IntelliJ IDEA.
 * User: eran
 * Date: 8/24/13
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Example6 {


    public static void main(String[] args){

        cl_platform_id[] platforms = new cl_platform_id[1];
        clGetPlatformIDs(1, platforms,null);
        cl_platform_id platform = platforms[0];
        cl_device_id[] devices = new cl_device_id[1];
        clGetDeviceIDs(platform,  CL_DEVICE_TYPE_GPU, 1, devices, null);
        cl_device_id device = devices[0];

        // CL_DEVICE_NAME
        String deviceName = getString(device, CL_DEVICE_NAME);
        System.out.println("--- Info for device "+deviceName+": ---");
        System.out.printf("CL_DEVICE_NAME: \t\t\t%s\n", deviceName);

        // CL_DEVICE_VENDOR
        String deviceVendor = getString(device, CL_DEVICE_VENDOR);
        System.out.printf("CL_DEVICE_VENDOR: \t\t\t%s\n", deviceVendor);

        // CL_DRIVER_VERSION
        String driverVersion = getString(device, CL_DRIVER_VERSION);
        System.out.printf("CL_DRIVER_VERSION: \t\t\t%s\n", driverVersion);

        // CL_DEVICE_MAX_COMPUTE_UNITS
        int maxComputeUnits = getInt(device, CL_DEVICE_MAX_COMPUTE_UNITS);
        System.out.printf("CL_DEVICE_MAX_COMPUTE_UNITS:\t\t%d\n", maxComputeUnits);

        // Initialize the context properties
        cl_context_properties contextProperties = new cl_context_properties();
        contextProperties.addProperty(CL_CONTEXT_PLATFORM, platform);

        // Create a context for the selected device
        cl_context context = clCreateContext(
                contextProperties, 1, new cl_device_id[]{device},
                null, null, null);

        // Create a command-queue for the selected device
        cl_command_queue commandQueue =
                clCreateCommandQueue(context, device, 0, null);

        // Read the program sources and compile them :
        String src = "__kernel void add_floats(__global const float* a, __global const float* b, __global float* out) \n" +
                "{\n" +
                "    int i = get_global_id(0);\n" +
                "    out[i] = a[i] + b[i];\n" +
                "}\n" +
                "\n" +
                "__kernel void fill_in_values(__global float* a, __global float* b) \n" +
                "{\n" +
                "    int i = get_global_id(0);\n" +
                "    a[i] = cos((float)i);\n" +
                "    b[i] = sin((float)i);\n" +
                "}";

        // Create the program from the source code
        cl_program program = clCreateProgramWithSource(context,
                1, new String[]{ src }, null, null);

        // Build the program
        clBuildProgram(program, 0, null, null, null, null);

        // Create the kernel
        cl_kernel kernel = clCreateKernel(program, "fill_in_values", null);



        final long tmp = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis());
        final int n = 1024*256*16;
        float srcArrayA[] = new float[n];
        float srcArrayB[] = new float[n];
        float dstArray[] = new float[n];
        for (int i=0; i<n; i++)
        {
            srcArrayA[i] = i;
            srcArrayB[i] = 2*i;
        }
        Pointer srcA = Pointer.to(srcArrayA);
        Pointer srcB = Pointer.to(srcArrayB);
        Pointer dst = Pointer.to(dstArray);

        // Allocate the memory objects for the input- and output data
        cl_mem memObjects[] = new cl_mem[4];
        memObjects[0] = clCreateBuffer(context,
                CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_float * n, srcA, null);
        memObjects[1] = clCreateBuffer(context,
                CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_float * n, srcB, null);
        memObjects[2] = clCreateBuffer(context,
                CL_MEM_READ_WRITE,
                Sizeof.cl_float * n, null, null);

        System.out.println(System.currentTimeMillis()-tmp);

        // Set the arguments for the kernel
        clSetKernelArg(kernel, 0,
                Sizeof.cl_mem, Pointer.to(memObjects[0]));
        clSetKernelArg(kernel, 1,
                Sizeof.cl_mem, Pointer.to(memObjects[1]));
        //clSetKernelArg(kernel, 2,
        //        Sizeof.cl_mem, Pointer.to(memObjects[2]));


        System.out.println((System.currentTimeMillis() - tmp));

        // Set the work-item dimensions
        long global_work_size[] = new long[]{n};
        long local_work_size[] = new long[]{1};

        // Execute the kernel
        clEnqueueNDRangeKernel(commandQueue, kernel, 1, null,
                global_work_size, local_work_size, 0, null, null);

        // Read the output data
        clEnqueueReadBuffer(commandQueue, memObjects[2], CL_TRUE, 0,
                n * Sizeof.cl_float, dst, 0, null, null);
        clEnqueueReadBuffer(commandQueue, memObjects[0], CL_TRUE, 0,
                n * Sizeof.cl_float, srcA, 0, null, null);
        clEnqueueReadBuffer(commandQueue, memObjects[1], CL_TRUE, 0,
                n * Sizeof.cl_float, srcB, 0, null, null);




        System.out.println((System.currentTimeMillis()-tmp));

        for ( int i = 0; i < 10; ++i){
            System.out.println( srcArrayA[i] + " " + srcArrayB[i] + " " + dstArray[i] );
        }

        float[] aa = new float[n];
        float[] bb = new float[n];
        float[] cc = new float[n];

        for ( int i = 0; i < n; ++i){
            aa[i] = (float) i;
            bb[i] = (float) 2*i;
            aa[i] = (float) Math.sin(i);
            bb[i] = (float) Math.cos(i);
        }
        System.out.println("xx" + (System.currentTimeMillis() - tmp));
        for ( int i = 0; i < n; ++i){
            //cc[i] = aa[i] + bb[i];
        }

        System.out.println("xxx" + (System.currentTimeMillis() - tmp));


    }

    /**
     * Returns the value of the device info parameter with the given name
     *
     * @param device The device
     * @param paramName The parameter name
     * @return The value
     */
    private static String getString(cl_device_id device, int paramName)
    {
        // Obtain the length of the string that will be queried
        long size[] = new long[1];
        clGetDeviceInfo(device, paramName, 0, null, size);

        // Create a buffer of the appropriate size and fill it with the info
        byte buffer[] = new byte[(int)size[0]];
        clGetDeviceInfo(device, paramName, buffer.length, org.jocl.Pointer.to(buffer), null);

        // Create a string from the buffer (excluding the trailing \0 byte)
        return new String(buffer, 0, buffer.length-1);
    }

    /**
     * Returns the value of the platform info parameter with the given name
     *
     * @param platform The platform
     * @param paramName The parameter name
     * @return The value
     */
    private static String getString(cl_platform_id platform, int paramName)
    {
        // Obtain the length of the string that will be queried
        long size[] = new long[1];

        clGetPlatformInfo(platform, paramName, 0, null, size);

        // Create a buffer of the appropriate size and fill it with the info
        byte buffer[] = new byte[(int)size[0]];
        clGetPlatformInfo(platform, paramName, buffer.length, org.jocl.Pointer.to(buffer), null);

        // Create a string from the buffer (excluding the trailing \0 byte)
        return new String(buffer, 0, buffer.length-1);
    }

    /**
     * Returns the value of the device info parameter with the given name
     *
     * @param device The device
     * @param paramName The parameter name
     * @return The value
     */
    private static long getLong(cl_device_id device, int paramName)
    {
        return getLongs(device, paramName, 1)[0];
    }

    /**
     * Returns the values of the device info parameter with the given name
     *
     * @param device The device
     * @param paramName The parameter name
     * @param numValues The number of values
     * @return The value
     */
    private static long[] getLongs(cl_device_id device, int paramName, int numValues)
    {
        long values[] = new long[numValues];
        clGetDeviceInfo(device, paramName, Sizeof.cl_long * numValues, org.jocl.Pointer.to(values), null);
        return values;
    }

    /**
     * Returns the value of the device info parameter with the given name
     *
     * @param device The device
     * @param paramName The parameter name
     * @return The value
     */
    private static int getInt(cl_device_id device, int paramName)
    {
        return getInts(device, paramName, 1)[0];
    }

    /**
     * Returns the values of the device info parameter with the given name
     *
     * @param device The device
     * @param paramName The parameter name
     * @param numValues The number of values
     * @return The value
     */
    private static int[] getInts(cl_device_id device, int paramName, int numValues)
    {
        int values[] = new int[numValues];
        clGetDeviceInfo(device, paramName, Sizeof.cl_int * numValues, org.jocl.Pointer.to(values), null);
        return values;
    }

    /**
     * Returns the value of the device info parameter with the given name
     *
     * @param device The device
     * @param paramName The parameter name
     * @return The value
     */
    private static long getSize(cl_device_id device, int paramName)
    {
        return getSizes(device, paramName, 1)[0];
    }

    /**
     * Returns the values of the device info parameter with the given name
     *
     * @param device The device
     * @param paramName The parameter name
     * @param numValues The number of values
     * @return The value
     */
    static long[] getSizes(cl_device_id device, int paramName, int numValues)
    {
        // The size of the returned data has to depend on
        // the size of a size_t, which is handled here
        ByteBuffer buffer = ByteBuffer.allocate(
                numValues * Sizeof.size_t).order(ByteOrder.nativeOrder());
        clGetDeviceInfo(device, paramName, Sizeof.size_t * numValues,
                org.jocl.Pointer.to(buffer), null);
        long values[] = new long[numValues];
        if (Sizeof.size_t == 4)
        {
            for (int i=0; i<numValues; i++)
            {
                values[i] = buffer.getInt(i * Sizeof.size_t);
            }
        }
        else
        {
            for (int i=0; i<numValues; i++)
            {
                values[i] = buffer.getLong(i * Sizeof.size_t);
            }
        }
        return values;
    }



}
