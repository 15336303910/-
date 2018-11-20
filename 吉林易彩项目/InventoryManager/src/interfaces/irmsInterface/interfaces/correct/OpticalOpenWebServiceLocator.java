/**
 * OpticalOpenWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package interfaces.irmsInterface.interfaces.correct;

public class OpticalOpenWebServiceLocator extends org.apache.axis.client.Service implements interfaces.irmsInterface.interfaces.correct.OpticalOpenWebService {

    public OpticalOpenWebServiceLocator() {
    }


    public OpticalOpenWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OpticalOpenWebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for opticalOpenWebServiceHttpPort
    private java.lang.String opticalOpenWebServiceHttpPort_address = "http://10.154.36.98:7000/irms/services/opticalOpenWebService";

    public java.lang.String getopticalOpenWebServiceHttpPortAddress() {
        return opticalOpenWebServiceHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String opticalOpenWebServiceHttpPortWSDDServiceName = "opticalOpenWebServiceHttpPort";

    public java.lang.String getopticalOpenWebServiceHttpPortWSDDServiceName() {
        return opticalOpenWebServiceHttpPortWSDDServiceName;
    }

    public void setopticalOpenWebServiceHttpPortWSDDServiceName(java.lang.String name) {
        opticalOpenWebServiceHttpPortWSDDServiceName = name;
    }

    public interfaces.irmsInterface.interfaces.correct.OpticalOpenWebServicePortType getopticalOpenWebServiceHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(opticalOpenWebServiceHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getopticalOpenWebServiceHttpPort(endpoint);
    }

    public interfaces.irmsInterface.interfaces.correct.OpticalOpenWebServicePortType getopticalOpenWebServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            interfaces.irmsInterface.interfaces.correct.OpticalOpenWebServiceHttpBindingStub _stub = new interfaces.irmsInterface.interfaces.correct.OpticalOpenWebServiceHttpBindingStub(portAddress, this);
            _stub.setPortName(getopticalOpenWebServiceHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setopticalOpenWebServiceHttpPortEndpointAddress(java.lang.String address) {
        opticalOpenWebServiceHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (interfaces.irmsInterface.interfaces.correct.OpticalOpenWebServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                interfaces.irmsInterface.interfaces.correct.OpticalOpenWebServiceHttpBindingStub _stub = new interfaces.irmsInterface.interfaces.correct.OpticalOpenWebServiceHttpBindingStub(new java.net.URL(opticalOpenWebServiceHttpPort_address), this);
                _stub.setPortName(getopticalOpenWebServiceHttpPortWSDDServiceName());
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
        if ("opticalOpenWebServiceHttpPort".equals(inputPortName)) {
            return getopticalOpenWebServiceHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.circuit.hn.app.inspur.com", "opticalOpenWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.circuit.hn.app.inspur.com", "opticalOpenWebServiceHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("opticalOpenWebServiceHttpPort".equals(portName)) {
            setopticalOpenWebServiceHttpPortEndpointAddress(address);
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
