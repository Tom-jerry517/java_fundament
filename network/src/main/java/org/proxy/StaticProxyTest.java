package org.proxy;

/**
 * @author cy
 */
public class StaticProxyTest {
    public static void main(String[] args) {
        ProxyClothFactory proxyClothFactory=new ProxyClothFactory(new NikeClothFactory());
        proxyClothFactory.produceCloth();
    }
}

interface ClothFactory{
    void produceCloth();
}

class ProxyClothFactory implements ClothFactory{
    private ClothFactory factory;

    public ProxyClothFactory(ClothFactory factory){
        this.factory=factory;
    }
    @Override
    public void produceCloth() {
        System.out.println("代理工厂做一些准备工作！");
        factory.produceCloth();
        System.out.println("代理工厂做后续工作！");
    }
}

class NikeClothFactory implements ClothFactory{

    @Override
    public void produceCloth() {
        System.out.println("Nike...");
    }
}