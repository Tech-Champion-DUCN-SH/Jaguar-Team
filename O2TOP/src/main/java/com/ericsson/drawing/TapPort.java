package com.ericsson.drawing;

public class TapPort extends O2Port {

        private qbrBridge _qbr;
        public TapPort(String name, String macAddr, qbrBridge qbr) {
                super(name, macAddr);
                _qbr = qbr;
        }
        public qbrBridge GetBridge(){
                return _qbr;
        }
}
