/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.cscore.raw;

import java.nio.ByteBuffer;

import edu.wpi.cscore.CameraServerJNI;

/**
 * Class for storing raw frame data between image read call.
 *
 * <p>Data is reused for each frame read, rather then reallocating every frame.
 */
public class RawFrame implements AutoCloseable {
  private final long m_framePtr;
  private ByteBuffer m_dataByteBuffer;
  private long m_dataPtr;
  private int m_totalData;
  private int m_width;
  private int m_height;
  private int m_pixelFormat;

  /**
   * Construct a new RawFrame.
   */
  public RawFrame() {
    m_framePtr = CameraServerJNI.allocateRawFrame();
  }

  /**
   * Close the RawFrame, releasing native resources.
   * Any images currently using the data will be invalidated.
   */
  @Override
  public void close() {
    CameraServerJNI.freeRawFrame(m_framePtr);
  }

  /**
   * Called from JNI to set data in class.
   */
  public void setData(ByteBuffer dataByteBuffer, long dataPtr, int totalData,
                      int width, int height, int pixelFormat) {
    m_dataByteBuffer = dataByteBuffer;
    m_dataPtr = dataPtr;
    m_totalData = totalData;
    m_width = width;
    m_height = height;
    m_pixelFormat = pixelFormat;
  }

  /**
   * Get the pointer to native representation of this frame.
   */
  public long getFramePtr() {
    return m_framePtr;
  }

  /**
   * Get a ByteBuffer pointing to the frame data.
   * This ByteBuffer is backed by the frame directly. Its lifetime is controlled by
   * the frame. If a new frame gets read, it will overwrite the current one.
   */
  public ByteBuffer getDataByteBuffer() {
    return m_dataByteBuffer;
  }

  /**
   * Get a long (is a char* in native code) pointing to the frame data.
   * This pointer is backed by the frame directly. Its lifetime is controlled by
   * the frame. If a new frame gets read, it will overwrite the current one.
   */
  public long getDataPtr() {
    return m_dataPtr;
  }

  /**
   * Get the total length of the data stored in the frame.
   */
  public int getTotalData() {
    return m_totalData;
  }

  /**
   * Get the width of the frame.
   */
  public int getWidth() {
    return m_width;
  }

  /**
   * Set the width of the frame.
   */
  public void setWidth(int width) {
    this.m_width = width;
  }

  /**
   * Get the height of the frame.
   */
  public int getHeight() {
    return m_height;
  }

  /**
   * Set the height of the frame.
   */
  public void setHeight(int height) {
    this.m_height = height;
  }

  /**
   * Get the PixelFormat of the frame.
   */
  public int getPixelFormat() {
    return m_pixelFormat;
  }

  /**
   * Set the PixelFormat of the frame.
   */
  public void setPixelFormat(int pixelFormat) {
    this.m_pixelFormat = pixelFormat;
  }
}
