package components;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class ComponentClassLoader extends URLClassLoader {


    public ComponentClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public ComponentClassLoader(URL[] urls) {
        super(urls);
    }

    public ComponentClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public ComponentClassLoader(String name, URL[] urls, ClassLoader parent) {
        super(name, urls, parent);
    }

    public ComponentClassLoader(String name, URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(name, urls, parent, factory);
    }



}
