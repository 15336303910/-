/**
 * CellMountApp2IRMSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package interfaces.irmsInterface.interfaces.auther;

public class CellMountApp2IRMSServiceLocator extends org.apache.axis.client.Service implements interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSService {

    public CellMountApp2IRMSServiceLocator() {
    }


    public CellMountApp2IRMSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CellMountApp2IRMSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CellMountApp2IRMSServiceHttpPort
    //外网地址http://111.8.17.230:7021/CellMountTestNew/services/CellMountApp2IRMSService
    //内网地址http://10.154.13.83:7001/CellMountTestNew/services/CellMountApp2IRMSService
    private java.lang.String CellMountApp2IRMSServiceHttpPort_address = "http://111.8.17.230:7021/CellMountTestNew/services/CellMountApp2IRMSService";

    public java.lang.String getCellMountApp2IRMSServiceHttpPortAddress() {
        return CellMountApp2IRMSServiceHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CellMountApp2IRMSServiceHttpPortWSDDServiceName = "CellMountApp2IRMSServiceHttpPort";

    public java.lang.String getCellMountApp2IRMSServiceHttpPortWSDDServiceName() {
        return CellMountApp2IRMSServiceHttpPortWSDDServiceName;
    }

    public void setCellMountApp2IRMSServiceHttpPortWSDDServiceName(java.lang.String name) {
        CellMountApp2IRMSServiceHttpPortWSDDServiceName = name;
    }

    public interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSServicePortType getCellMountApp2IRMSServiceHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CellMountApp2IRMSServiceHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCellMountApp2IRMSServiceHttpPort(endpoint);
    }

    public interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSServicePortType getCellMountApp2IRMSServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSServiceHttpBindingStub _stub = new interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSServiceHttpBindingStub(portAddress, this);
            _stub.setPortName(getCellMountApp2IRMSServiceHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCellMountApp2IRMSServiceHttpPortEndpointAddress(java.lang.String address) {
        CellMountApp2IRMSServiceHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSServiceHttpBindingStub _stub = new interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSServiceHttpBindingStub(new java.net.URL(CellMountApp2IRMSServiceHttpPort_address), this);
                _stub.setPortName(getCellMountApp2IRMSServiceHttpPortWSDDServiceName());
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
        if ("CellMountApp2IRMSServiceHttpPort".equals(inputPortName)) {
            return getCellMountApp2IRMSServiceHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://inspur.com", "CellMountApp2IRMSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://inspur.com", "CellMountApp2IRMSServiceHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CellMountApp2IRMSServiceHttpPort".equals(portName)) {
            setCellMountApp2IRMSServiceHttpPortEndpointAddress(address);
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
