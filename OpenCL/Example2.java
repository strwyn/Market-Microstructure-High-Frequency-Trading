package class6;

import com.nativelibs4java.opencl.CLDevice;
import com.nativelibs4java.opencl.CLPlatform;
import com.nativelibs4java.opencl.JavaCL;
import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_device_id;
import org.jocl.cl_platform_id;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import static org.jocl.CL.*;
import static org.jocl.CL.clGetPlatformInfo;

/**
 * In this example we are going to print the platform details for the computer and all th devices in each platform
 */
public class Example2 {
    public static void main(String[] args){

        // Obtain the number of platforms
        int numPlatforms[] = new int[1];
        clGetPlatformIDs(0, null, numPlatforms);

        System.out.println("Number of platforms: "+numPlatforms[0]);

        // Obtain the platform IDs
        cl_platform_id platforms[] = new cl_platform_id[numPlatforms[0]];
        clGetPlatformIDs(platforms.length, platforms, null);

        // Collect all devices of all platforms
        List<cl_device_id> devices = new ArrayList<cl_device_id>();
        for (cl_platform_id platform : platforms)
        {

            System.out.println( "Platform Name: " + getString(platform, CL_PLATFORM_NAME));
            System.out.println( "Vendoe: " + getString(platform, CL_PLATFORM_VENDOR));

            // Obtain the number of devices for the current platform
            int numDevices[] = new int[1];
            clGetDeviceIDs(platform, CL_DEVICE_TYPE_ALL, 0, null, numDevices);
            System.out.println("Number of devices: " + numDevices[0]);

            // start iterating over devices
            cl_device_id devicesArray[] = new cl_device_id[numDevices[0]];
            clGetDeviceIDs(platform, CL_DEVICE_TYPE_ALL, numDevices[0], devicesArray, null);
            for (cl_device_id device : devicesArray ){
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

                // CL_DEVICE_TYPE
                long deviceType = getLong(device, CL_DEVICE_TYPE);
                if( (deviceType & CL_DEVICE_TYPE_CPU) != 0)
                    System.out.printf("CL_DEVICE_TYPE:\t\t\t\t%s\n", "CL_DEVICE_TYPE_CPU");
                if( (deviceType & CL_DEVICE_TYPE_GPU) != 0)
                    System.out.printf("CL_DEVICE_TYPE:\t\t\t\t%s\n", "CL_DEVICE_TYPE_GPU");
                if( (deviceType & CL_DEVICE_TYPE_ACCELERATOR) != 0)
                    System.out.printf("CL_DEVICE_TYPE:\t\t\t\t%s\n", "CL_DEVICE_TYPE_ACCELERATOR");
                if( (deviceType & CL_DEVICE_TYPE_DEFAULT) != 0)
                    System.out.printf("CL_DEVICE_TYPE:\t\t\t\t%s\n", "CL_DEVICE_TYPE_DEFAULT");

                // CL_DEVICE_MAX_COMPUTE_UNITS
                int maxComputeUnits = getInt(device, CL_DEVICE_MAX_COMPUTE_UNITS);
                System.out.printf("CL_DEVICE_MAX_COMPUTE_UNITS:\t\t%d\n", maxComputeUnits);

                // CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS
                long maxWorkItemDimensions = getLong(device, CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS);
                System.out.printf("CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS:\t%d\n", maxWorkItemDimensions);

                // CL_DEVICE_MAX_WORK_ITEM_SIZES
                long maxWorkItemSizes[] = getSizes(device, CL_DEVICE_MAX_WORK_ITEM_SIZES, 3);
                System.out.printf("CL_DEVICE_MAX_WORK_ITEM_SIZES:\t\t%d / %d / %d \n",
                        maxWorkItemSizes[0], maxWorkItemSizes[1], maxWorkItemSizes[2]);

                // CL_DEVICE_MAX_WORK_GROUP_SIZE
                long maxWorkGroupSize = getSize(device, CL_DEVICE_MAX_WORK_GROUP_SIZE);
                System.out.printf("CL_DEVICE_MAX_WORK_GROUP_SIZE:\t\t%d\n", maxWorkGroupSize);

                // CL_DEVICE_MAX_CLOCK_FREQUENCY
                long maxClockFrequency = getLong(device, CL_DEVICE_MAX_CLOCK_FREQUENCY);
                System.out.printf("CL_DEVICE_MAX_CLOCK_FREQUENCY:\t\t%d MHz\n", maxClockFrequency);

                // CL_DEVICE_ADDRESS_BITS
                int addressBits = getInt(device, CL_DEVICE_ADDRESS_BITS);
                System.out.printf("CL_DEVICE_ADDRESS_BITS:\t\t\t%d\n", addressBits);

                // CL_DEVICE_MAX_MEM_ALLOC_SIZE
                long maxMemAllocSize = getLong(device, CL_DEVICE_MAX_MEM_ALLOC_SIZE);
                System.out.printf("CL_DEVICE_MAX_MEM_ALLOC_SIZE:\t\t%d MByte\n", (int)(maxMemAllocSize / (1024 * 1024)));

                // CL_DEVICE_GLOBAL_MEM_SIZE
                long globalMemSize = getLong(device, CL_DEVICE_GLOBAL_MEM_SIZE);
                System.out.printf("CL_DEVICE_GLOBAL_MEM_SIZE:\t\t%d MByte\n", (int)(globalMemSize / (1024 * 1024)));

                // CL_DEVICE_ERROR_CORRECTION_SUPPORT
                int errorCorrectionSupport = getInt(device, CL_DEVICE_ERROR_CORRECTION_SUPPORT);
                System.out.printf("CL_DEVICE_ERROR_CORRECTION_SUPPORT:\t%s\n", errorCorrectionSupport != 0 ? "yes" : "no");

                // CL_DEVICE_LOCAL_MEM_TYPE
                int localMemType = getInt(device, CL_DEVICE_LOCAL_MEM_TYPE);
                System.out.printf("CL_DEVICE_LOCAL_MEM_TYPE:\t\t%s\n", localMemType == 1 ? "local" : "global");

                // CL_DEVICE_LOCAL_MEM_SIZE
                long localMemSize = getLong(device, CL_DEVICE_LOCAL_MEM_SIZE);
                System.out.printf("CL_DEVICE_LOCAL_MEM_SIZE:\t\t%d KByte\n", (int)(localMemSize / 1024));



            }
        }

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
        clGetDeviceInfo(device, paramName, buffer.length, Pointer.to(buffer), null);

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
        clGetPlatformInfo(platform, paramName, buffer.length, Pointer.to(buffer), null);

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
        clGetDeviceInfo(device, paramName, Sizeof.cl_long * numValues, Pointer.to(values), null);
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
        clGetDeviceInfo(device, paramName, Sizeof.cl_int * numValues, Pointer.to(values), null);
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
                Pointer.to(buffer), null);
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
