package co.herod.adbwrapper.model;

public class AdbUiHierarchy {

    private final String xmlString;
    private final AdbDevice adbDevice;

    public AdbUiHierarchy(final String xmlString, final AdbDevice adbDevice) {

        this.xmlString = xmlString;
        this.adbDevice = adbDevice;
    }

    public String getXmlString() {
        return xmlString;
    }

    public AdbDevice getAdbDevice() {
        return adbDevice;
    }

    @Override
    public String toString() {
        return "AdbUiHierarchy{" +
                "xmlString='" + xmlString + '\'' +
                ", adbDevice=" + adbDevice +
                '}';
    }
}
