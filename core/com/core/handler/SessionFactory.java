package com.core.handler;

import javax.servlet.http.HttpSession;

public final class SessionFactory
{
  private static ThreadLocal<HttpSession> threadLocal = new ThreadLocal<HttpSession>();
  
  public static String getUsrName()
  {
    HttpSession session = (HttpSession)threadLocal.get();
    if (session == null) {
      return null;
    }
    return session.getAttribute("usrNam").toString();
  }
  
  public static String getFullName()
  {
    return ((HttpSession)threadLocal.get()).getAttribute("fullName").toString();
  }
  
  public static String getDept()
  {
    return ((HttpSession)threadLocal.get()).getAttribute("dept").toString();
  }
  
  public static String getRole()
  {
    return ((HttpSession)threadLocal.get()).getAttribute("role").toString();
  }
  
  public static HttpSession getSession()
  {
    return (HttpSession)threadLocal.get();
  }
  
  public static void setSession(HttpSession session)
  {
    threadLocal.set(session);
  }
  
  public static String setAttribute(Object value)
  {
    String className = Thread.currentThread().getStackTrace()[2].getClassName();
    ((HttpSession)threadLocal.get()).setAttribute(className, value);
    return className;
  }
  
  public static void setAttribute(String key, Object value)
  {
    ((HttpSession)threadLocal.get()).setAttribute(key, value);
  }
  
  public static Object getAttribute(String key)
  {
    return ((HttpSession)threadLocal.get()).getAttribute(key);
  }
  
  public static void removeAttribute(String key)
  {
    ((HttpSession)threadLocal.get()).removeAttribute(key);
  }
}
