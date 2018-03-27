
package com.alibaba.dubbo.rpc.gen.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class $__DemoStub {

    public interface Iface {

        public boolean echoBool(boolean arg);

        public byte echoByte(byte arg);

        public short echoI16(short arg);

        public int echoI32(int arg);

        public long echoI64(long arg);

        public double echoDouble(double arg);

        public String echoString(String arg);

    }

    public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor implements org.apache.thrift.TProcessor {
        private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());

        public Processor(I iface) {
            super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
        }

        protected Processor(I iface, Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
            super(iface, getProcessMap(processMap));
        }

        private static <I extends Iface> Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> getProcessMap(Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
            processMap.put("echoBool", new echoBool());
            processMap.put("echoByte", new echoByte());
            processMap.put("echoI16", new echoI16());
            processMap.put("echoI32", new echoI32());
            processMap.put("echoI64", new echoI64());
            processMap.put("echoDouble", new echoDouble());
            processMap.put("echoString", new echoString());
            return processMap;
        }

        private static class echoBool<I extends Iface> extends org.apache.thrift.ProcessFunction<I, echoBool_args> {
            public echoBool() {
                super("echoBool");
            }

            protected echoBool_args getEmptyArgsInstance() {
                return new echoBool_args();
            }

            protected echoBool_result getResult(I iface, echoBool_args args) throws org.apache.thrift.TException {
                echoBool_result result = new echoBool_result();
                result.success = iface.echoBool(args.arg);
                result.setSuccessIsSet(true);
                return result;
            }
        }

        private static class echoByte<I extends Iface> extends org.apache.thrift.ProcessFunction<I, echoByte_args> {
            public echoByte() {
                super("echoByte");
            }

            protected echoByte_args getEmptyArgsInstance() {
                return new echoByte_args();
            }

            protected echoByte_result getResult(I iface, echoByte_args args) throws org.apache.thrift.TException {
                echoByte_result result = new echoByte_result();
                result.success = iface.echoByte(args.arg);
                result.setSuccessIsSet(true);
                return result;
            }
        }

        private static class echoI16<I extends Iface> extends org.apache.thrift.ProcessFunction<I, echoI16_args> {
            public echoI16() {
                super("echoI16");
            }

            protected echoI16_args getEmptyArgsInstance() {
                return new echoI16_args();
            }

            protected echoI16_result getResult(I iface, echoI16_args args) throws org.apache.thrift.TException {
                echoI16_result result = new echoI16_result();
                result.success = iface.echoI16(args.arg);
                result.setSuccessIsSet(true);
                return result;
            }
        }

        private static class echoI32<I extends Iface> extends org.apache.thrift.ProcessFunction<I, echoI32_args> {
            public echoI32() {
                super("echoI32");
            }

            protected echoI32_args getEmptyArgsInstance() {
                return new echoI32_args();
            }

            protected echoI32_result getResult(I iface, echoI32_args args) throws org.apache.thrift.TException {
                echoI32_result result = new echoI32_result();
                result.success = iface.echoI32(args.arg);
                result.setSuccessIsSet(true);
                return result;
            }
        }

        private static class echoI64<I extends Iface> extends org.apache.thrift.ProcessFunction<I, echoI64_args> {
            public echoI64() {
                super("echoI64");
            }

            protected echoI64_args getEmptyArgsInstance() {
                return new echoI64_args();
            }

            protected echoI64_result getResult(I iface, echoI64_args args) throws org.apache.thrift.TException {
                echoI64_result result = new echoI64_result();
                result.success = iface.echoI64(args.arg);
                result.setSuccessIsSet(true);
                return result;
            }
        }

        private static class echoDouble<I extends Iface> extends org.apache.thrift.ProcessFunction<I, echoDouble_args> {
            public echoDouble() {
                super("echoDouble");
            }

            protected echoDouble_args getEmptyArgsInstance() {
                return new echoDouble_args();
            }

            protected echoDouble_result getResult(I iface, echoDouble_args args) throws org.apache.thrift.TException {
                echoDouble_result result = new echoDouble_result();
                result.success = iface.echoDouble(args.arg);
                result.setSuccessIsSet(true);
                return result;
            }
        }

        private static class echoString<I extends Iface> extends org.apache.thrift.ProcessFunction<I, echoString_args> {
            public echoString() {
                super("echoString");
            }

            protected echoString_args getEmptyArgsInstance() {
                return new echoString_args();
            }

            protected echoString_result getResult(I iface, echoString_args args) throws org.apache.thrift.TException {
                echoString_result result = new echoString_result();
                result.success = iface.echoString(args.arg);
                return result;
            }
        }

    }

    public static class echoBool_args implements org.apache.thrift.TBase<echoBool_args, echoBool_args._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoBool_args");
        private static final org.apache.thrift.protocol.TField ARG_FIELD_DESC = new org.apache.thrift.protocol.TField("arg", org.apache.thrift.protocol.TType.BOOL, (short) 1);
                private static final int __ARG_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.ARG, new org.apache.thrift.meta_data.FieldMetaData("arg", org.apache.thrift.TFieldRequirementType.REQUIRED,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoBool_args.class, metaDataMap);
        }

        public boolean arg;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoBool_args() {
        }

        public echoBool_args(
                boolean arg) {
            this();
            this.arg = arg;
            setArgIsSet(true);
        }

        
        public echoBool_args(echoBool_args other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.arg = other.arg;
        }

        public echoBool_args deepCopy() {
            return new echoBool_args(this);
        }

        public void clear() {
            setArgIsSet(false);
            this.arg = false;
        }

        public boolean isArg() {
            return this.arg;
        }

        public echoBool_args setArg(boolean arg) {
            this.arg = arg;
            setArgIsSet(true);
            return this;
        }

        public void unsetArg() {
            __isset_bit_vector.clear(__ARG_ISSET_ID);
        }

        
        public boolean isSetArg() {
            return __isset_bit_vector.get(__ARG_ISSET_ID);
        }

        public void setArgIsSet(boolean value) {
            __isset_bit_vector.set(__ARG_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case ARG:
                    if (value == null) {
                        unsetArg();
                    } else {
                        setArg((Boolean) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case ARG:
                    return Boolean.valueOf(isArg());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case ARG:
                    return isSetArg();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoBool_args)
                return this.equals((echoBool_args) that);
            return false;
        }

        public boolean equals(echoBool_args that) {
            if (that == null)
                return false;

            boolean this_present_arg = true;
            boolean that_present_arg = true;
            if (this_present_arg || that_present_arg) {
                if (!(this_present_arg && that_present_arg))
                    return false;
                if (this.arg != that.arg)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoBool_args other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoBool_args typedOther = (echoBool_args) other;

            lastComparison = Boolean.valueOf(isSetArg()).compareTo(typedOther.isSetArg());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetArg()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.arg, typedOther.arg);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 1:                         if (field.type == org.apache.thrift.protocol.TType.BOOL) {
                            this.arg = iprot.readBool();
                            setArgIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        if (!isSetArg()) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'arg' was not found in serialized data! Struct: " + toString());
            }
            validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            validate();

            oprot.writeStructBegin(STRUCT_DESC);
            oprot.writeFieldBegin(ARG_FIELD_DESC);
            oprot.writeBool(this.arg);
            oprot.writeFieldEnd();
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoBool_args(");
            boolean first = true;

            sb.append("arg:");
            sb.append(this.arg);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                                }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            ARG((short) 1, "arg");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 1:                         return ARG;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoBool_result implements org.apache.thrift.TBase<echoBool_result, echoBool_result._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoBool_result");
        private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.BOOL, (short) 0);
                private static final int __SUCCESS_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoBool_result.class, metaDataMap);
        }

        public boolean success;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoBool_result() {
        }

        public echoBool_result(
                boolean success) {
            this();
            this.success = success;
            setSuccessIsSet(true);
        }

        
        public echoBool_result(echoBool_result other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.success = other.success;
        }

        public echoBool_result deepCopy() {
            return new echoBool_result(this);
        }

        public void clear() {
            setSuccessIsSet(false);
            this.success = false;
        }

        public boolean isSuccess() {
            return this.success;
        }

        public echoBool_result setSuccess(boolean success) {
            this.success = success;
            setSuccessIsSet(true);
            return this;
        }

        public void unsetSuccess() {
            __isset_bit_vector.clear(__SUCCESS_ISSET_ID);
        }

        
        public boolean isSetSuccess() {
            return __isset_bit_vector.get(__SUCCESS_ISSET_ID);
        }

        public void setSuccessIsSet(boolean value) {
            __isset_bit_vector.set(__SUCCESS_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case SUCCESS:
                    if (value == null) {
                        unsetSuccess();
                    } else {
                        setSuccess((Boolean) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case SUCCESS:
                    return Boolean.valueOf(isSuccess());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case SUCCESS:
                    return isSetSuccess();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoBool_result)
                return this.equals((echoBool_result) that);
            return false;
        }

        public boolean equals(echoBool_result that) {
            if (that == null)
                return false;

            boolean this_present_success = true;
            boolean that_present_success = true;
            if (this_present_success || that_present_success) {
                if (!(this_present_success && that_present_success))
                    return false;
                if (this.success != that.success)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoBool_result other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoBool_result typedOther = (echoBool_result) other;

            lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetSuccess()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 0:                         if (field.type == org.apache.thrift.protocol.TType.BOOL) {
                            this.success = iprot.readBool();
                            setSuccessIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            oprot.writeStructBegin(STRUCT_DESC);

            if (this.isSetSuccess()) {
                oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
                oprot.writeBool(this.success);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoBool_result(");
            boolean first = true;

            sb.append("success:");
            sb.append(this.success);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                    }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            SUCCESS((short) 0, "success");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 0:                         return SUCCESS;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoByte_args implements org.apache.thrift.TBase<echoByte_args, echoByte_args._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoByte_args");
        private static final org.apache.thrift.protocol.TField ARG_FIELD_DESC = new org.apache.thrift.protocol.TField("arg", org.apache.thrift.protocol.TType.BYTE, (short) 1);
                private static final int __ARG_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.ARG, new org.apache.thrift.meta_data.FieldMetaData("arg", org.apache.thrift.TFieldRequirementType.REQUIRED,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoByte_args.class, metaDataMap);
        }

        public byte arg;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoByte_args() {
        }

        public echoByte_args(
                byte arg) {
            this();
            this.arg = arg;
            setArgIsSet(true);
        }

        
        public echoByte_args(echoByte_args other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.arg = other.arg;
        }

        public echoByte_args deepCopy() {
            return new echoByte_args(this);
        }

        public void clear() {
            setArgIsSet(false);
            this.arg = 0;
        }

        public byte getArg() {
            return this.arg;
        }

        public echoByte_args setArg(byte arg) {
            this.arg = arg;
            setArgIsSet(true);
            return this;
        }

        public void unsetArg() {
            __isset_bit_vector.clear(__ARG_ISSET_ID);
        }

        
        public boolean isSetArg() {
            return __isset_bit_vector.get(__ARG_ISSET_ID);
        }

        public void setArgIsSet(boolean value) {
            __isset_bit_vector.set(__ARG_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case ARG:
                    if (value == null) {
                        unsetArg();
                    } else {
                        setArg((Byte) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case ARG:
                    return Byte.valueOf(getArg());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case ARG:
                    return isSetArg();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoByte_args)
                return this.equals((echoByte_args) that);
            return false;
        }

        public boolean equals(echoByte_args that) {
            if (that == null)
                return false;

            boolean this_present_arg = true;
            boolean that_present_arg = true;
            if (this_present_arg || that_present_arg) {
                if (!(this_present_arg && that_present_arg))
                    return false;
                if (this.arg != that.arg)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoByte_args other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoByte_args typedOther = (echoByte_args) other;

            lastComparison = Boolean.valueOf(isSetArg()).compareTo(typedOther.isSetArg());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetArg()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.arg, typedOther.arg);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 1:                         if (field.type == org.apache.thrift.protocol.TType.BYTE) {
                            this.arg = iprot.readByte();
                            setArgIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        if (!isSetArg()) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'arg' was not found in serialized data! Struct: " + toString());
            }
            validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            validate();

            oprot.writeStructBegin(STRUCT_DESC);
            oprot.writeFieldBegin(ARG_FIELD_DESC);
            oprot.writeByte(this.arg);
            oprot.writeFieldEnd();
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoByte_args(");
            boolean first = true;

            sb.append("arg:");
            sb.append(this.arg);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                                }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            ARG((short) 1, "arg");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 1:                         return ARG;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoByte_result implements org.apache.thrift.TBase<echoByte_result, echoByte_result._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoByte_result");
        private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.BYTE, (short) 0);
                private static final int __SUCCESS_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoByte_result.class, metaDataMap);
        }

        public byte success;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoByte_result() {
        }

        public echoByte_result(
                byte success) {
            this();
            this.success = success;
            setSuccessIsSet(true);
        }

        
        public echoByte_result(echoByte_result other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.success = other.success;
        }

        public echoByte_result deepCopy() {
            return new echoByte_result(this);
        }

        public void clear() {
            setSuccessIsSet(false);
            this.success = 0;
        }

        public byte getSuccess() {
            return this.success;
        }

        public echoByte_result setSuccess(byte success) {
            this.success = success;
            setSuccessIsSet(true);
            return this;
        }

        public void unsetSuccess() {
            __isset_bit_vector.clear(__SUCCESS_ISSET_ID);
        }

        
        public boolean isSetSuccess() {
            return __isset_bit_vector.get(__SUCCESS_ISSET_ID);
        }

        public void setSuccessIsSet(boolean value) {
            __isset_bit_vector.set(__SUCCESS_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case SUCCESS:
                    if (value == null) {
                        unsetSuccess();
                    } else {
                        setSuccess((Byte) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case SUCCESS:
                    return Byte.valueOf(getSuccess());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case SUCCESS:
                    return isSetSuccess();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoByte_result)
                return this.equals((echoByte_result) that);
            return false;
        }

        public boolean equals(echoByte_result that) {
            if (that == null)
                return false;

            boolean this_present_success = true;
            boolean that_present_success = true;
            if (this_present_success || that_present_success) {
                if (!(this_present_success && that_present_success))
                    return false;
                if (this.success != that.success)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoByte_result other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoByte_result typedOther = (echoByte_result) other;

            lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetSuccess()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 0:                         if (field.type == org.apache.thrift.protocol.TType.BYTE) {
                            this.success = iprot.readByte();
                            setSuccessIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            oprot.writeStructBegin(STRUCT_DESC);

            if (this.isSetSuccess()) {
                oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
                oprot.writeByte(this.success);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoByte_result(");
            boolean first = true;

            sb.append("success:");
            sb.append(this.success);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                    }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            SUCCESS((short) 0, "success");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 0:                         return SUCCESS;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoI16_args implements org.apache.thrift.TBase<echoI16_args, echoI16_args._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoI16_args");
        private static final org.apache.thrift.protocol.TField ARG_FIELD_DESC = new org.apache.thrift.protocol.TField("arg", org.apache.thrift.protocol.TType.I16, (short) 1);
                private static final int __ARG_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.ARG, new org.apache.thrift.meta_data.FieldMetaData("arg", org.apache.thrift.TFieldRequirementType.REQUIRED,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoI16_args.class, metaDataMap);
        }

        public short arg;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoI16_args() {
        }

        public echoI16_args(
                short arg) {
            this();
            this.arg = arg;
            setArgIsSet(true);
        }

        
        public echoI16_args(echoI16_args other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.arg = other.arg;
        }

        public echoI16_args deepCopy() {
            return new echoI16_args(this);
        }

        public void clear() {
            setArgIsSet(false);
            this.arg = 0;
        }

        public short getArg() {
            return this.arg;
        }

        public echoI16_args setArg(short arg) {
            this.arg = arg;
            setArgIsSet(true);
            return this;
        }

        public void unsetArg() {
            __isset_bit_vector.clear(__ARG_ISSET_ID);
        }

        
        public boolean isSetArg() {
            return __isset_bit_vector.get(__ARG_ISSET_ID);
        }

        public void setArgIsSet(boolean value) {
            __isset_bit_vector.set(__ARG_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case ARG:
                    if (value == null) {
                        unsetArg();
                    } else {
                        setArg((Short) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case ARG:
                    return Short.valueOf(getArg());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case ARG:
                    return isSetArg();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoI16_args)
                return this.equals((echoI16_args) that);
            return false;
        }

        public boolean equals(echoI16_args that) {
            if (that == null)
                return false;

            boolean this_present_arg = true;
            boolean that_present_arg = true;
            if (this_present_arg || that_present_arg) {
                if (!(this_present_arg && that_present_arg))
                    return false;
                if (this.arg != that.arg)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoI16_args other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoI16_args typedOther = (echoI16_args) other;

            lastComparison = Boolean.valueOf(isSetArg()).compareTo(typedOther.isSetArg());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetArg()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.arg, typedOther.arg);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 1:                         if (field.type == org.apache.thrift.protocol.TType.I16) {
                            this.arg = iprot.readI16();
                            setArgIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        if (!isSetArg()) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'arg' was not found in serialized data! Struct: " + toString());
            }
            validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            validate();

            oprot.writeStructBegin(STRUCT_DESC);
            oprot.writeFieldBegin(ARG_FIELD_DESC);
            oprot.writeI16(this.arg);
            oprot.writeFieldEnd();
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoI16_args(");
            boolean first = true;

            sb.append("arg:");
            sb.append(this.arg);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                                }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            ARG((short) 1, "arg");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 1:                         return ARG;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoI16_result implements org.apache.thrift.TBase<echoI16_result, echoI16_result._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoI16_result");
        private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.I16, (short) 0);
                private static final int __SUCCESS_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoI16_result.class, metaDataMap);
        }

        public short success;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoI16_result() {
        }

        public echoI16_result(
                short success) {
            this();
            this.success = success;
            setSuccessIsSet(true);
        }

        
        public echoI16_result(echoI16_result other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.success = other.success;
        }

        public echoI16_result deepCopy() {
            return new echoI16_result(this);
        }

        public void clear() {
            setSuccessIsSet(false);
            this.success = 0;
        }

        public short getSuccess() {
            return this.success;
        }

        public echoI16_result setSuccess(short success) {
            this.success = success;
            setSuccessIsSet(true);
            return this;
        }

        public void unsetSuccess() {
            __isset_bit_vector.clear(__SUCCESS_ISSET_ID);
        }

        
        public boolean isSetSuccess() {
            return __isset_bit_vector.get(__SUCCESS_ISSET_ID);
        }

        public void setSuccessIsSet(boolean value) {
            __isset_bit_vector.set(__SUCCESS_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case SUCCESS:
                    if (value == null) {
                        unsetSuccess();
                    } else {
                        setSuccess((Short) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case SUCCESS:
                    return Short.valueOf(getSuccess());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case SUCCESS:
                    return isSetSuccess();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoI16_result)
                return this.equals((echoI16_result) that);
            return false;
        }

        public boolean equals(echoI16_result that) {
            if (that == null)
                return false;

            boolean this_present_success = true;
            boolean that_present_success = true;
            if (this_present_success || that_present_success) {
                if (!(this_present_success && that_present_success))
                    return false;
                if (this.success != that.success)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoI16_result other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoI16_result typedOther = (echoI16_result) other;

            lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetSuccess()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 0:                         if (field.type == org.apache.thrift.protocol.TType.I16) {
                            this.success = iprot.readI16();
                            setSuccessIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            oprot.writeStructBegin(STRUCT_DESC);

            if (this.isSetSuccess()) {
                oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
                oprot.writeI16(this.success);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoI16_result(");
            boolean first = true;

            sb.append("success:");
            sb.append(this.success);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                    }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            SUCCESS((short) 0, "success");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 0:                         return SUCCESS;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoI32_args implements org.apache.thrift.TBase<echoI32_args, echoI32_args._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoI32_args");
        private static final org.apache.thrift.protocol.TField ARG_FIELD_DESC = new org.apache.thrift.protocol.TField("arg", org.apache.thrift.protocol.TType.I32, (short) 1);
                private static final int __ARG_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.ARG, new org.apache.thrift.meta_data.FieldMetaData("arg", org.apache.thrift.TFieldRequirementType.REQUIRED,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoI32_args.class, metaDataMap);
        }

        public int arg;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoI32_args() {
        }

        public echoI32_args(
                int arg) {
            this();
            this.arg = arg;
            setArgIsSet(true);
        }

        
        public echoI32_args(echoI32_args other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.arg = other.arg;
        }

        public echoI32_args deepCopy() {
            return new echoI32_args(this);
        }

        public void clear() {
            setArgIsSet(false);
            this.arg = 0;
        }

        public int getArg() {
            return this.arg;
        }

        public echoI32_args setArg(int arg) {
            this.arg = arg;
            setArgIsSet(true);
            return this;
        }

        public void unsetArg() {
            __isset_bit_vector.clear(__ARG_ISSET_ID);
        }

        
        public boolean isSetArg() {
            return __isset_bit_vector.get(__ARG_ISSET_ID);
        }

        public void setArgIsSet(boolean value) {
            __isset_bit_vector.set(__ARG_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case ARG:
                    if (value == null) {
                        unsetArg();
                    } else {
                        setArg((Integer) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case ARG:
                    return Integer.valueOf(getArg());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case ARG:
                    return isSetArg();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoI32_args)
                return this.equals((echoI32_args) that);
            return false;
        }

        public boolean equals(echoI32_args that) {
            if (that == null)
                return false;

            boolean this_present_arg = true;
            boolean that_present_arg = true;
            if (this_present_arg || that_present_arg) {
                if (!(this_present_arg && that_present_arg))
                    return false;
                if (this.arg != that.arg)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoI32_args other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoI32_args typedOther = (echoI32_args) other;

            lastComparison = Boolean.valueOf(isSetArg()).compareTo(typedOther.isSetArg());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetArg()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.arg, typedOther.arg);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 1:                         if (field.type == org.apache.thrift.protocol.TType.I32) {
                            this.arg = iprot.readI32();
                            setArgIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        if (!isSetArg()) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'arg' was not found in serialized data! Struct: " + toString());
            }
            validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            validate();

            oprot.writeStructBegin(STRUCT_DESC);
            oprot.writeFieldBegin(ARG_FIELD_DESC);
            oprot.writeI32(this.arg);
            oprot.writeFieldEnd();
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoI32_args(");
            boolean first = true;

            sb.append("arg:");
            sb.append(this.arg);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                                }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            ARG((short) 1, "arg");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 1:                         return ARG;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoI32_result implements org.apache.thrift.TBase<echoI32_result, echoI32_result._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoI32_result");
        private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.I32, (short) 0);
                private static final int __SUCCESS_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoI32_result.class, metaDataMap);
        }

        public int success;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoI32_result() {
        }

        public echoI32_result(
                int success) {
            this();
            this.success = success;
            setSuccessIsSet(true);
        }

        
        public echoI32_result(echoI32_result other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.success = other.success;
        }

        public echoI32_result deepCopy() {
            return new echoI32_result(this);
        }

        public void clear() {
            setSuccessIsSet(false);
            this.success = 0;
        }

        public int getSuccess() {
            return this.success;
        }

        public echoI32_result setSuccess(int success) {
            this.success = success;
            setSuccessIsSet(true);
            return this;
        }

        public void unsetSuccess() {
            __isset_bit_vector.clear(__SUCCESS_ISSET_ID);
        }

        
        public boolean isSetSuccess() {
            return __isset_bit_vector.get(__SUCCESS_ISSET_ID);
        }

        public void setSuccessIsSet(boolean value) {
            __isset_bit_vector.set(__SUCCESS_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case SUCCESS:
                    if (value == null) {
                        unsetSuccess();
                    } else {
                        setSuccess((Integer) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case SUCCESS:
                    return Integer.valueOf(getSuccess());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case SUCCESS:
                    return isSetSuccess();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoI32_result)
                return this.equals((echoI32_result) that);
            return false;
        }

        public boolean equals(echoI32_result that) {
            if (that == null)
                return false;

            boolean this_present_success = true;
            boolean that_present_success = true;
            if (this_present_success || that_present_success) {
                if (!(this_present_success && that_present_success))
                    return false;
                if (this.success != that.success)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoI32_result other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoI32_result typedOther = (echoI32_result) other;

            lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetSuccess()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 0:                         if (field.type == org.apache.thrift.protocol.TType.I32) {
                            this.success = iprot.readI32();
                            setSuccessIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            oprot.writeStructBegin(STRUCT_DESC);

            if (this.isSetSuccess()) {
                oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
                oprot.writeI32(this.success);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoI32_result(");
            boolean first = true;

            sb.append("success:");
            sb.append(this.success);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                    }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            SUCCESS((short) 0, "success");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 0:                         return SUCCESS;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoI64_args implements org.apache.thrift.TBase<echoI64_args, echoI64_args._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoI64_args");
        private static final org.apache.thrift.protocol.TField ARG_FIELD_DESC = new org.apache.thrift.protocol.TField("arg", org.apache.thrift.protocol.TType.I64, (short) 1);
                private static final int __ARG_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.ARG, new org.apache.thrift.meta_data.FieldMetaData("arg", org.apache.thrift.TFieldRequirementType.REQUIRED,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoI64_args.class, metaDataMap);
        }

        public long arg;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoI64_args() {
        }

        public echoI64_args(
                long arg) {
            this();
            this.arg = arg;
            setArgIsSet(true);
        }

        
        public echoI64_args(echoI64_args other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.arg = other.arg;
        }

        public echoI64_args deepCopy() {
            return new echoI64_args(this);
        }

        public void clear() {
            setArgIsSet(false);
            this.arg = 0;
        }

        public long getArg() {
            return this.arg;
        }

        public echoI64_args setArg(long arg) {
            this.arg = arg;
            setArgIsSet(true);
            return this;
        }

        public void unsetArg() {
            __isset_bit_vector.clear(__ARG_ISSET_ID);
        }

        
        public boolean isSetArg() {
            return __isset_bit_vector.get(__ARG_ISSET_ID);
        }

        public void setArgIsSet(boolean value) {
            __isset_bit_vector.set(__ARG_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case ARG:
                    if (value == null) {
                        unsetArg();
                    } else {
                        setArg((Long) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case ARG:
                    return Long.valueOf(getArg());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case ARG:
                    return isSetArg();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoI64_args)
                return this.equals((echoI64_args) that);
            return false;
        }

        public boolean equals(echoI64_args that) {
            if (that == null)
                return false;

            boolean this_present_arg = true;
            boolean that_present_arg = true;
            if (this_present_arg || that_present_arg) {
                if (!(this_present_arg && that_present_arg))
                    return false;
                if (this.arg != that.arg)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoI64_args other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoI64_args typedOther = (echoI64_args) other;

            lastComparison = Boolean.valueOf(isSetArg()).compareTo(typedOther.isSetArg());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetArg()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.arg, typedOther.arg);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 1:                         if (field.type == org.apache.thrift.protocol.TType.I64) {
                            this.arg = iprot.readI64();
                            setArgIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        if (!isSetArg()) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'arg' was not found in serialized data! Struct: " + toString());
            }
            validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            validate();

            oprot.writeStructBegin(STRUCT_DESC);
            oprot.writeFieldBegin(ARG_FIELD_DESC);
            oprot.writeI64(this.arg);
            oprot.writeFieldEnd();
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoI64_args(");
            boolean first = true;

            sb.append("arg:");
            sb.append(this.arg);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                                }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            ARG((short) 1, "arg");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 1:                         return ARG;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoI64_result implements org.apache.thrift.TBase<echoI64_result, echoI64_result._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoI64_result");
        private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.I64, (short) 0);
                private static final int __SUCCESS_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoI64_result.class, metaDataMap);
        }

        public long success;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoI64_result() {
        }

        public echoI64_result(
                long success) {
            this();
            this.success = success;
            setSuccessIsSet(true);
        }

        
        public echoI64_result(echoI64_result other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.success = other.success;
        }

        public echoI64_result deepCopy() {
            return new echoI64_result(this);
        }

        public void clear() {
            setSuccessIsSet(false);
            this.success = 0;
        }

        public long getSuccess() {
            return this.success;
        }

        public echoI64_result setSuccess(long success) {
            this.success = success;
            setSuccessIsSet(true);
            return this;
        }

        public void unsetSuccess() {
            __isset_bit_vector.clear(__SUCCESS_ISSET_ID);
        }

        
        public boolean isSetSuccess() {
            return __isset_bit_vector.get(__SUCCESS_ISSET_ID);
        }

        public void setSuccessIsSet(boolean value) {
            __isset_bit_vector.set(__SUCCESS_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case SUCCESS:
                    if (value == null) {
                        unsetSuccess();
                    } else {
                        setSuccess((Long) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case SUCCESS:
                    return Long.valueOf(getSuccess());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case SUCCESS:
                    return isSetSuccess();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoI64_result)
                return this.equals((echoI64_result) that);
            return false;
        }

        public boolean equals(echoI64_result that) {
            if (that == null)
                return false;

            boolean this_present_success = true;
            boolean that_present_success = true;
            if (this_present_success || that_present_success) {
                if (!(this_present_success && that_present_success))
                    return false;
                if (this.success != that.success)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoI64_result other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoI64_result typedOther = (echoI64_result) other;

            lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetSuccess()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 0:                         if (field.type == org.apache.thrift.protocol.TType.I64) {
                            this.success = iprot.readI64();
                            setSuccessIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            oprot.writeStructBegin(STRUCT_DESC);

            if (this.isSetSuccess()) {
                oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
                oprot.writeI64(this.success);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoI64_result(");
            boolean first = true;

            sb.append("success:");
            sb.append(this.success);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                    }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            SUCCESS((short) 0, "success");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 0:                         return SUCCESS;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoDouble_args implements org.apache.thrift.TBase<echoDouble_args, echoDouble_args._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoDouble_args");
        private static final org.apache.thrift.protocol.TField ARG_FIELD_DESC = new org.apache.thrift.protocol.TField("arg", org.apache.thrift.protocol.TType.DOUBLE, (short) 1);
                private static final int __ARG_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.ARG, new org.apache.thrift.meta_data.FieldMetaData("arg", org.apache.thrift.TFieldRequirementType.REQUIRED,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoDouble_args.class, metaDataMap);
        }

        public double arg;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoDouble_args() {
        }

        public echoDouble_args(
                double arg) {
            this();
            this.arg = arg;
            setArgIsSet(true);
        }

        
        public echoDouble_args(echoDouble_args other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.arg = other.arg;
        }

        public echoDouble_args deepCopy() {
            return new echoDouble_args(this);
        }

        public void clear() {
            setArgIsSet(false);
            this.arg = 0.0;
        }

        public double getArg() {
            return this.arg;
        }

        public echoDouble_args setArg(double arg) {
            this.arg = arg;
            setArgIsSet(true);
            return this;
        }

        public void unsetArg() {
            __isset_bit_vector.clear(__ARG_ISSET_ID);
        }

        
        public boolean isSetArg() {
            return __isset_bit_vector.get(__ARG_ISSET_ID);
        }

        public void setArgIsSet(boolean value) {
            __isset_bit_vector.set(__ARG_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case ARG:
                    if (value == null) {
                        unsetArg();
                    } else {
                        setArg((Double) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case ARG:
                    return Double.valueOf(getArg());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case ARG:
                    return isSetArg();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoDouble_args)
                return this.equals((echoDouble_args) that);
            return false;
        }

        public boolean equals(echoDouble_args that) {
            if (that == null)
                return false;

            boolean this_present_arg = true;
            boolean that_present_arg = true;
            if (this_present_arg || that_present_arg) {
                if (!(this_present_arg && that_present_arg))
                    return false;
                if (this.arg != that.arg)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoDouble_args other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoDouble_args typedOther = (echoDouble_args) other;

            lastComparison = Boolean.valueOf(isSetArg()).compareTo(typedOther.isSetArg());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetArg()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.arg, typedOther.arg);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 1:                         if (field.type == org.apache.thrift.protocol.TType.DOUBLE) {
                            this.arg = iprot.readDouble();
                            setArgIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        if (!isSetArg()) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'arg' was not found in serialized data! Struct: " + toString());
            }
            validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            validate();

            oprot.writeStructBegin(STRUCT_DESC);
            oprot.writeFieldBegin(ARG_FIELD_DESC);
            oprot.writeDouble(this.arg);
            oprot.writeFieldEnd();
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoDouble_args(");
            boolean first = true;

            sb.append("arg:");
            sb.append(this.arg);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                                }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            ARG((short) 1, "arg");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 1:                         return ARG;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoDouble_result implements org.apache.thrift.TBase<echoDouble_result, echoDouble_result._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoDouble_result");
        private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.DOUBLE, (short) 0);
                private static final int __SUCCESS_ISSET_ID = 0;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoDouble_result.class, metaDataMap);
        }

        public double success;         private BitSet __isset_bit_vector = new BitSet(1);

        public echoDouble_result() {
        }

        public echoDouble_result(
                double success) {
            this();
            this.success = success;
            setSuccessIsSet(true);
        }

        
        public echoDouble_result(echoDouble_result other) {
            __isset_bit_vector.clear();
            __isset_bit_vector.or(other.__isset_bit_vector);
            this.success = other.success;
        }

        public echoDouble_result deepCopy() {
            return new echoDouble_result(this);
        }

        public void clear() {
            setSuccessIsSet(false);
            this.success = 0.0;
        }

        public double getSuccess() {
            return this.success;
        }

        public echoDouble_result setSuccess(double success) {
            this.success = success;
            setSuccessIsSet(true);
            return this;
        }

        public void unsetSuccess() {
            __isset_bit_vector.clear(__SUCCESS_ISSET_ID);
        }

        
        public boolean isSetSuccess() {
            return __isset_bit_vector.get(__SUCCESS_ISSET_ID);
        }

        public void setSuccessIsSet(boolean value) {
            __isset_bit_vector.set(__SUCCESS_ISSET_ID, value);
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case SUCCESS:
                    if (value == null) {
                        unsetSuccess();
                    } else {
                        setSuccess((Double) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case SUCCESS:
                    return Double.valueOf(getSuccess());

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case SUCCESS:
                    return isSetSuccess();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoDouble_result)
                return this.equals((echoDouble_result) that);
            return false;
        }

        public boolean equals(echoDouble_result that) {
            if (that == null)
                return false;

            boolean this_present_success = true;
            boolean that_present_success = true;
            if (this_present_success || that_present_success) {
                if (!(this_present_success && that_present_success))
                    return false;
                if (this.success != that.success)
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoDouble_result other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoDouble_result typedOther = (echoDouble_result) other;

            lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetSuccess()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 0:                         if (field.type == org.apache.thrift.protocol.TType.DOUBLE) {
                            this.success = iprot.readDouble();
                            setSuccessIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            oprot.writeStructBegin(STRUCT_DESC);

            if (this.isSetSuccess()) {
                oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
                oprot.writeDouble(this.success);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoDouble_result(");
            boolean first = true;

            sb.append("success:");
            sb.append(this.success);
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                    }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                                __isset_bit_vector = new BitSet(1);
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            SUCCESS((short) 0, "success");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 0:                         return SUCCESS;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoString_args implements org.apache.thrift.TBase<echoString_args, echoString_args._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoString_args");
        private static final org.apache.thrift.protocol.TField ARG_FIELD_DESC = new org.apache.thrift.protocol.TField("arg", org.apache.thrift.protocol.TType.STRING, (short) 1);

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.ARG, new org.apache.thrift.meta_data.FieldMetaData("arg", org.apache.thrift.TFieldRequirementType.REQUIRED,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoString_args.class, metaDataMap);
        }

        
        public String arg; 
        public echoString_args() {
        }

        public echoString_args(
                String arg) {
            this();
            this.arg = arg;
        }

        
        public echoString_args(echoString_args other) {
            if (other.isSetArg()) {
                this.arg = other.arg;
            }
        }

        public echoString_args deepCopy() {
            return new echoString_args(this);
        }

        public void clear() {
            this.arg = null;
        }

        public String getArg() {
            return this.arg;
        }

        public echoString_args setArg(String arg) {
            this.arg = arg;
            return this;
        }

        public void unsetArg() {
            this.arg = null;
        }

        
        public boolean isSetArg() {
            return this.arg != null;
        }

        public void setArgIsSet(boolean value) {
            if (!value) {
                this.arg = null;
            }
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case ARG:
                    if (value == null) {
                        unsetArg();
                    } else {
                        setArg((String) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case ARG:
                    return getArg();

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case ARG:
                    return isSetArg();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoString_args)
                return this.equals((echoString_args) that);
            return false;
        }

        public boolean equals(echoString_args that) {
            if (that == null)
                return false;

            boolean this_present_arg = true && this.isSetArg();
            boolean that_present_arg = true && that.isSetArg();
            if (this_present_arg || that_present_arg) {
                if (!(this_present_arg && that_present_arg))
                    return false;
                if (!this.arg.equals(that.arg))
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoString_args other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoString_args typedOther = (echoString_args) other;

            lastComparison = Boolean.valueOf(isSetArg()).compareTo(typedOther.isSetArg());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetArg()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.arg, typedOther.arg);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 1:                         if (field.type == org.apache.thrift.protocol.TType.STRING) {
                            this.arg = iprot.readString();
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (this.arg != null) {
                oprot.writeFieldBegin(ARG_FIELD_DESC);
                oprot.writeString(this.arg);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoString_args(");
            boolean first = true;

            sb.append("arg:");
            if (this.arg == null) {
                sb.append("null");
            } else {
                sb.append(this.arg);
            }
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                        if (arg == null) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'arg' was not present! Struct: " + toString());
            }
        }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            ARG((short) 1, "arg");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 1:                         return ARG;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

    public static class echoString_result implements org.apache.thrift.TBase<echoString_result, echoString_result._Fields>, java.io.Serializable, Cloneable {
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("echoString_result");
        private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRING, (short) 0);

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT,
                    new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(echoString_result.class, metaDataMap);
        }

        
        public String success; 
        public echoString_result() {
        }

        public echoString_result(
                String success) {
            this();
            this.success = success;
        }

        
        public echoString_result(echoString_result other) {
            if (other.isSetSuccess()) {
                this.success = other.success;
            }
        }

        public echoString_result deepCopy() {
            return new echoString_result(this);
        }

        public void clear() {
            this.success = null;
        }

        public String getSuccess() {
            return this.success;
        }

        public echoString_result setSuccess(String success) {
            this.success = success;
            return this;
        }

        public void unsetSuccess() {
            this.success = null;
        }

        
        public boolean isSetSuccess() {
            return this.success != null;
        }

        public void setSuccessIsSet(boolean value) {
            if (!value) {
                this.success = null;
            }
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case SUCCESS:
                    if (value == null) {
                        unsetSuccess();
                    } else {
                        setSuccess((String) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case SUCCESS:
                    return getSuccess();

            }
            throw new IllegalStateException();
        }

        
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case SUCCESS:
                    return isSetSuccess();
            }
            throw new IllegalStateException();
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof echoString_result)
                return this.equals((echoString_result) that);
            return false;
        }

        public boolean equals(echoString_result that) {
            if (that == null)
                return false;

            boolean this_present_success = true && this.isSetSuccess();
            boolean that_present_success = true && that.isSetSuccess();
            if (this_present_success || that_present_success) {
                if (!(this_present_success && that_present_success))
                    return false;
                if (!this.success.equals(that.success))
                    return false;
            }

            return true;
        }

        public int hashCode() {
            return 0;
        }

        public int compareTo(echoString_result other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            echoString_result typedOther = (echoString_result) other;

            lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetSuccess()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 0:                         if (field.type == org.apache.thrift.protocol.TType.STRING) {
                            this.success = iprot.readString();
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

                        validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            oprot.writeStructBegin(STRUCT_DESC);

            if (this.isSetSuccess()) {
                oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
                oprot.writeString(this.success);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("echoString_result(");
            boolean first = true;

            sb.append("success:");
            if (this.success == null) {
                sb.append("null");
            } else {
                sb.append(this.success);
            }
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
                    }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            SUCCESS((short) 0, "success");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 0:                         return SUCCESS;
                    default:
                        return null;
                }
            }

            
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

    }

}
