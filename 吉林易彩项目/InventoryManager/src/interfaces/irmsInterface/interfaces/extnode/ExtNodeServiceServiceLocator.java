/**
 * ExtNodeServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package interfaces.irmsInterface.interfaces.extnode;

public class ExtNodeServiceServiceLocator extends org.apache.axis.client.Service implements interfaces.irmsInterface.interfaces.extnode.ExtNodeServiceService {

    public ExtNodeServiceServiceLocator() {
    }


    public ExtNodeServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ExtNodeServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for extNodeService
    private java.lang.String extNodeService_address = "http://10.18.11.46:8089/test/services/extNodeService";

    public java.lang.String getextNodeServiceAddress() {
        return extNodeService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String extNodeServiceWSDDServiceName = "extNodeService";

    public java.lang.String getextNodeServiceWSDDServiceName() {
        return extNodeServiceWSDDServiceName;
    }

    public void setextNodeServiceWSDDServiceName(java.lang.String name) {
        extNodeServiceWSDDServiceName = name;
    }

    public interfaces.irmsInterface.interfaces.extnode.ExtNodeService getextNodeService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(extNodeService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getextNodeService(endpoint);
    }

    public interfaces.irmsInterface.interfaces.extnode.ExtNodeService getextNodeService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            interfaces.irmsInterface.interfaces.extnode.ExtNodeServiceSoapBindingStub _stub = new interfaces.irmsInterface.interfaces.extnode.ExtNodeServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getextNodeServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setextNodeServiceEndpointAddress(java.lang.String address) {
        extNodeService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (interfaces.irmsInterface.interfaces.extnode.ExtNodeService.class.isAssignableFrom(serviceEndpointInterface)) {
                interfaces.irmsInterface.interfaces.extnode.ExtNodeServiceSoapBindingStub _stub = new interfaces.irmsInterface.interfaces.extnode.ExtNodeServiceSoapBindingStub(new java.net.URL(extNodeService_address), this);
                _stub.setPortName(getextNodeServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("extNodeService".equals(inputPortName)) {
            return getextNodeService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.18.11.46:8089/test/services/extNodeService", "ExtNodeServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.18.11.46:8089/test/services/extNodeService", "extNodeService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("extNodeService".equals(portName)) {
            setextNodeServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
