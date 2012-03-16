/*
 * Copyright (c) 2001-2004 Caucho Technology, Inc.  All rights reserved.
 *
 * The Apache Software License, Version 1.1
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Caucho Technology (http://www.caucho.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Hessian", "Resin", and "Caucho" must not be used to
 *    endorse or promote products derived from this software without prior
 *    written permission. For written permission, please contact
 *    info@caucho.com.
 *
 * 5. Products derived from this software may not be called "Resin"
 *    nor may "Resin" appear in their names without prior written
 *    permission of Caucho Technology.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL CAUCHO TECHNOLOGY OR ITS CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Scott Ferguson
 */

package com.caucho.hessian.jmx;

import java.io.IOException;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;

import com.caucho.hessian.io.AbstractDeserializer;
import com.caucho.hessian.io.AbstractHessianInput;

/**
 * Deserializing an MBeanInfo valued object
 */
public class MBeanInfoDeserializer extends AbstractDeserializer {
  public Class getType()
  {
    return MBeanInfo.class;
  }
  
  public Object readMap(AbstractHessianInput in)
    throws IOException
  {
    String className = null;
    String description = null;
    MBeanAttributeInfo []attributes = null;
    MBeanConstructorInfo []constructors = null;
    MBeanOperationInfo []operations = null;
    MBeanNotificationInfo []notifications = null;
    
    while (! in.isEnd()) {
      String key = in.readString();

      if ("className".equals(key))
	className = in.readString();
      else if ("description".equals(key))
	description = in.readString();
      else if ("attributes".equals(key)) {
	attributes = (MBeanAttributeInfo []) in.readObject(MBeanAttributeInfo[].class);
      }
      /*
      else if ("isWrite".equals(key))
	isWrite = in.readBoolean();
      else if ("isIs".equals(key))
	isIs = in.readBoolean();
      */
      else
	in.readObject();
    }

    in.readMapEnd();

    try {
      MBeanInfo info;
      
      info = new MBeanInfo(className, description, attributes,
			   constructors, operations, notifications);

      return info;
    } catch (Exception e) {
      throw new IOException(String.valueOf(e));
    }
  }
}
