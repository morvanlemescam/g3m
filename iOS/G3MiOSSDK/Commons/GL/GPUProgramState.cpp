//
//  GPUProgramState.cpp
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 05/04/13.
//
//

#include "GPUProgramState.hpp"

GPUProgramState::~GPUProgramState(){
  clear();
}

void GPUProgramState::clear(){
  _lastProgramUsed = NULL;
#ifdef C_CODE
  for(std::map<std::string, GPUUniformValue*> ::const_iterator it = _uniformValues.begin();
      it != _uniformValues.end();
      it++){
    delete it->second;
  }
#endif
  _uniformValues.clear();
  
#ifdef C_CODE
  for(std::map<std::string, GPUAttributeValue*> ::const_iterator it = _attributesValues.begin();
      it != _attributesValues.end();
      it++){
    delete it->second;
  }
#endif
  _attributesEnabled.clear();
  _attributesValues.clear();
}

void GPUProgramState::applyValuesToLinkedProgram() const{
#ifdef C_CODE
  for(std::map<std::string, GPUUniformValue*> ::const_iterator it = _uniformValues.begin();
      it != _uniformValues.end();
      it++){
    std::string name = it->first;
    GPUUniformValue* v = it->second;
    v->setValueToLinkedUniform();
  }
  
  for(std::map<std::string, attributeEnabledStruct> ::const_iterator it = _attributesEnabled.begin();
      it != _attributesEnabled.end();
      it++){
    GPUAttribute* a = it->second.attribute;
    if (a == NULL){
      ILogger::instance()->logError("NO ATTRIBUTE LINKED");
    } else{
      a->setEnable(it->second.value);
    }
  }
  
  for(std::map<std::string, GPUAttributeValue*> ::const_iterator it = _attributesValues.begin();
      it != _attributesValues.end();
      it++){
    GPUAttributeValue* v = it->second;
    v->setValueToLinkedAttribute();
  }
#endif
#ifdef JAVA_CODE
  {
    final Iterator it = _uniformValues.entrySet().iterator();
    while (it.hasNext()) {
      final Map.Entry pairs = (Map.Entry)it.next();
      final GPUUniformValue v = (GPUUniformValue) pairs.getValue();
      v.setValueToLinkedUniform();
    }
  }
  
  {
    final Iterator it = _attributesEnabled.entrySet().iterator();
    while (it.hasNext()) {
      final Map.Entry pairs = (Map.Entry)it.next();
      final attributeEnabledStruct a = (attributeEnabledStruct) pairs.getValue();
      if (a.attribute == null) {
        ILogger.instance().logError("NO ATTRIBUTE LINKED");
      }
      else
      {
        a.attribute.setEnable(a.value);
      }
    }
  }
  
  
  {
    final Iterator it = _attributesValues.entrySet().iterator();
    while (it.hasNext()) {
      final Map.Entry pairs = (Map.Entry)it.next();
      final GPUAttributeValue a = (GPUAttributeValue) pairs.getValue();
      a.setValueToLinkedAttribute();
    }
  }
#endif
}


void GPUProgramState::linkToProgram(GPUProgram& prog) const{
  
  _lastProgramUsed = &prog;
  
#ifdef JAVA_CODE
  final Iterator it = _uniformValues.entrySet().iterator();
  while (it.hasNext()) {
    final Map.Entry pairs = (Map.Entry)it.next();
    final String name =  (String) pairs.getKey();
    final GPUUniformValue v = (GPUUniformValue) pairs.getValue();
    
    
    int type = v.getType();
    GPUUniform u = prog.getUniformOfType(name, type);
    
    if (u == NULL){
      ILogger.instance().logError("UNIFORM " + name + " NOT FOUND");
    } else{
      v.linkToGPUUniform(u);
    }
  }
  
  final Iterator it = _attributesEnabled.entrySet().iterator();
  while (it.hasNext()) {
    final Map.Entry pairs = (Map.Entry)it.next();
    final String name =  (String) pairs.getKey();
    final attributeEnabledStruct ae = (attributeEnabledStruct) pairs.getValue();
    
    GPUAttribute a = prog.getGPUAttribute(name);
    if (a == NULL){
      ILogger.instance().logError("ATTRIBUTE NOT FOUND " + name + ". COULDN'T CHANGE ENABLED STATE.");
    } else{
      ae.attribute = a;
    }
  }
  
#endif
  
  
  for(std::map<std::string, GPUUniformValue*> ::const_iterator it = _uniformValues.begin();
      it != _uniformValues.end();
      it++){
    
    std::string name = it->first;
    GPUUniformValue* v = it->second;
    
    const int type = v->getType();
    
    GPUUniform* u = prog.getUniformOfType(name, type);
//    if (type == GLType::glBool()){
//      u = prog.getGPUUniformBool(name);
//    } else {
//      if (type == GLType::glVec2Float()){
//        u = prog.getGPUUniformVec2Float(name);
//      } else{
//        if (type == GLType::glVec4Float()){
//          u = prog.getGPUUniformVec4Float(name);
//        } else{
//          if (type == GLType::glFloat()){
//            u = prog.getGPUUniformFloat(name);
//          } else
//            if (type == GLType::glMatrix4Float()){
//              u = prog.getGPUUniformMatrix4Float(name);
//            }
//        }
//      }
//    }
    
    if (u == NULL){
      ILogger::instance()->logError("UNIFORM " + name + " NOT FOUND");
    } else{
      v->linkToGPUUniform(u);
    }
  }
  
  for(std::map<std::string, attributeEnabledStruct> ::const_iterator it = _attributesEnabled.begin();
      it != _attributesEnabled.end();
      it++){
    std::string name = it->first;
    GPUAttribute* a = prog.getGPUAttribute(name);
    if (a == NULL){
      ILogger::instance()->logError("ATTRIBUTE NOT FOUND " + name + ". COULDN'T CHANGE ENABLED STATE.");
    } else{
      it->second.attribute = a;
    }
  }
  
  for(std::map<std::string, GPUAttributeValue*> ::const_iterator it = _attributesValues.begin();
      it != _attributesValues.end();
      it++){
    
    std::string name = it->first;
    GPUAttributeValue* v = it->second;
    
    const int type = v->getType();
    const int size = v->getAttributeSize();
    if (type == GLType::glFloat() && size == 1){
      GPUAttributeVec1Float* a = prog.getGPUAttributeVec1Float(name);
      if (a == NULL){
        ILogger::instance()->logError("ATTRIBUTE NOT FOUND " + name);
      } else{
        v->linkToGPUAttribute(a);
      }
      continue;
    }
    
    if (type == GLType::glFloat() && size == 2){
      GPUAttributeVec2Float* a = prog.getGPUAttributeVec2Float(name);
      if (a == NULL){
        ILogger::instance()->logError("ATTRIBUTE NOT FOUND " + name);
      } else{
        v->linkToGPUAttribute(a);
      }
      continue;
    }
    
    if (type == GLType::glFloat() && size == 3){
      GPUAttribute* a = prog.getGPUAttributeVec3Float(name);
      if (a == NULL){
        a = prog.getGPUAttributeVec4Float(name); //A VEC3 COLUD BE STORED IN A VEC4 ATTRIBUTE
      }
      
      if (a == NULL){
        ILogger::instance()->logError("ATTRIBUTE NOT FOUND " + name);
      } else{
        v->linkToGPUAttribute(a);
      }
      continue;
    }
    
    if (type == GLType::glFloat() && size == 4){
      GPUAttributeVec4Float* a = prog.getGPUAttributeVec4Float(name);
      if (a == NULL){
        ILogger::instance()->logError("ATTRIBUTE NOT FOUND " + name);
      } else{
        v->linkToGPUAttribute(a);
      }
      continue;
    }
    
  }
}

void GPUProgramState::applyChanges(GL* gl) const{
  if (_lastProgramUsed == NULL){
    ILogger::instance()->logError("Trying to use unlinked GPUProgramState.");
  }
  applyValuesToLinkedProgram();
  _lastProgramUsed->applyChanges(gl);
}

void GPUProgramState::setUniformValue(const std::string& name, GPUUniformValue* v){
#ifdef C_CODE
  std::map<std::string, GPUUniformValue*> ::iterator it = _uniformValues.find(name);
  if (it != _uniformValues.end()){
    delete it->second;
  }
#endif
  _uniformValues[name] = v;
}

void GPUProgramState::setAttributeValue(const std::string& name, GPUAttributeValue* v){
#ifdef C_CODE
  std::map<std::string, GPUAttributeValue*> ::iterator it = _attributesValues.find(name);
  if (it != _attributesValues.end()){
    delete it->second;
  }
#endif
  _attributesValues[name] = v;
}

void GPUProgramState::setAttributeValue(const std::string& name,
                                        IFloatBuffer* buffer, int attributeSize,
                                        int arrayElementSize, int index, bool normalized, int stride){
  switch (attributeSize) {
    case 1:
      setAttributeValue(name, new GPUAttributeValueVec1Float(buffer, arrayElementSize, index, stride, normalized) );
      break;
      
    case 2:
      setAttributeValue(name, new GPUAttributeValueVec2Float(buffer, arrayElementSize, index, stride, normalized) );
      break;
      
    case 3:
      setAttributeValue(name, new GPUAttributeValueVec3Float(buffer, arrayElementSize, index, stride, normalized) );
      break;
      
    case 4:
      setAttributeValue(name, new GPUAttributeValueVec4Float(buffer, arrayElementSize, index, stride, normalized) );
      break;
      
    default:
      ILogger::instance()->logError("Invalid size for Attribute.");
      break;
  }
}

void GPUProgramState::setUniformValue(const std::string& name, bool b){
  setUniformValue(name, new GPUUniformValueBool(b) );
}

void GPUProgramState::setUniformValue(const std::string& name, float f){
  setUniformValue(name, new GPUUniformValueFloat(f));
}

void GPUProgramState::setUniformValue(const std::string& name, const Vector2D& v){
  setUniformValue(name, new GPUUniformValueVec2Float(v._x, v._y));
}

void GPUProgramState::setUniformValue(const std::string& name, double x, double y, double z, double w){
  setUniformValue(name, new GPUUniformValueVec4Float(x,y,z,w));
}

void GPUProgramState::setUniformValue(const std::string& name, const MutableMatrix44D* m){
  
#ifdef C_CODE
  for(std::map<std::string, GPUUniformValue*> ::iterator it = _uniformValues.begin();
      it != _uniformValues.end();
      it++){
    std::string thisName = it->first;
    GPUUniformValue* uv = (GPUUniformValue*)it->second;
    if (thisName.compare(name) == 0 && uv->getType() == GLType::glMatrix4Float()){
      GPUUniformValueMatrix4FloatStack* v = (GPUUniformValueMatrix4FloatStack*)it->second;
      v->loadMatrix(m);
      return;
    }
  }
#endif
#ifdef JAVA_CODE
  final Iterator it = _uniformValues.entrySet().iterator();
  while (it.hasNext()) {
    final Map.Entry pairs = (Map.Entry)it.next();
    final String thisName =  (String) pairs.getKey();
    final GPUUniformValue uv = (GPUUniformValue) pairs.getValue();
    if ((thisName.compareTo(name) == 0) && (uv.getType() == GLType.glMatrix4Float()))
    {
      final GPUUniformValueMatrix4FloatStack v = (GPUUniformValueMatrix4FloatStack)uv;
      v.loadMatrix(m);
      return;
    }
  }
#endif
  
  setUniformValue(name, new GPUUniformValueMatrix4FloatStack(m));
}

void GPUProgramState::multiplyUniformValue(const std::string& name, const MutableMatrix44D* m){
  
#ifdef C_CODE
  
  for(std::map<std::string, GPUUniformValue*> ::iterator it = _uniformValues.begin();
      it != _uniformValues.end();
      it++){
    std::string thisName = it->first;
    GPUUniformValue* uv = (GPUUniformValue*)it->second;
    if (thisName.compare(name) == 0 && uv->getType() == GLType::glMatrix4Float()){
      GPUUniformValueMatrix4FloatStack* v = (GPUUniformValueMatrix4FloatStack*)it->second;
      v->multiplyMatrix(m);
      return;
    }
  }
  
#endif
#ifdef JAVA_CODE
  final Iterator it = _uniformValues.entrySet().iterator();
  while (it.hasNext()) {
    final Map.Entry pairs = (Map.Entry)it.next();
    final String thisName =  (String) pairs.getKey();
    final GPUUniformValue uv = (GPUUniformValue) pairs.getValue();
    if ((thisName.compareTo(name) == 0) && (uv.getType() == GLType.glMatrix4Float()))
    {
      final GPUUniformValueMatrix4FloatStack v = (GPUUniformValueMatrix4FloatStack)uv;
      v.multiplyMatrix(m);
      return;
    }
  }
#endif
  
  ILogger::instance()->logError("CAN'T MULTIPLY UNLOADED MATRIX");
  
}

void GPUProgramState::setAttributeEnabled(const std::string& name, bool enabled){
  attributeEnabledStruct ae;
  ae.value = enabled;
  ae.attribute = NULL;
  
  _attributesEnabled[name] = ae;
}

std::string GPUProgramState::description() const{
  std::string desc = "PROGRAM STATE\n==========\n";
  //TODO: IMPLEMENT
#ifdef C_CODE
  for(std::map<std::string, GPUUniformValue*> ::const_iterator it = _uniformValues.begin();
      it != _uniformValues.end();
      it++){
    
    std::string name = it->first;
    GPUUniformValue* v = it->second;
    
    desc += "Uniform " + name + ":\n";
    desc += v->description() + "\n";
  }
  
  for(std::map<std::string, attributeEnabledStruct> ::const_iterator it = _attributesEnabled.begin();
      it != _attributesEnabled.end();
      it++){
    std::string name = it->first;
    desc += "Attribute " + name;
    if (it->second.value) desc += " ENABLED\n";
    else desc += " ENABLED\n";
  }
  
  for(std::map<std::string, GPUAttributeValue*> ::const_iterator it = _attributesValues.begin();
      it != _attributesValues.end();
      it++){
    
    std::string name = it->first;
    GPUAttributeValue* v = it->second;
    
    desc += "Attribute " + name + ":\n";
    desc += v->description() + "\n";
  }
#endif
  return desc;
}

std::vector<std::string> GPUProgramState::getUniformsNames() const{
  std::vector<std::string> us;
  
#ifdef C_CODE
    for(std::map<std::string, GPUUniformValue*> ::const_iterator it = /*state->*/_uniformValues.begin();
        it != _uniformValues.end();
        it++){
      us.push_back(it->first);
    }
#endif
  
#ifdef JAVA_CODE
  final Iterator it = _uniformValues.entrySet().iterator();
  while (it.hasNext()) {
    final Map.Entry pairs = (Map.Entry)it.next();
    us.add((String)pairs.getKey());
  }
#endif

  return us;
}

bool GPUProgramState::isLinkableToProgram(const GPUProgram& program) const{
#ifdef C_CODE
  if (program.getGPUAttributesNumber() != _attributesEnabled.size()){
    return false;
  }
  
  int nDisabledAtt = 0;
  for(std::map<std::string, attributeEnabledStruct> ::const_iterator it = _attributesEnabled.begin();
      it != _attributesEnabled.end();
      it++){
    if (it->second.value == false){
      nDisabledAtt++;
    }
    if (program.getGPUAttribute(it->first) == NULL){
      return false;
    }
  }
  
  if (program.getGPUAttributesNumber() != _attributesValues.size() + nDisabledAtt){
    return false;
  }
  
  if (program.getGPUUniformsNumber()   != _uniformValues.size()){
    return false;
  }
  
  for(std::map<std::string, GPUUniformValue*> ::const_iterator it = _uniformValues.begin();
      it != _uniformValues.end();
      it++){
    if (program.getGPUUniform(it->first) == NULL){
      return false;
    }
  }
  
  for(std::map<std::string, GPUAttributeValue*> ::const_iterator it = _attributesValues.begin();
      it != _attributesValues.end();
      it++){
    if (program.getGPUAttribute(it->first) == NULL){
      return false;
    }
  }
  
  return true;
#endif
#ifdef JAVA_CODE
  if (program.getGPUAttributesNumber() != _attributesEnabled.size())
  {
    return false;
  }
  
  int nDisabledAtt = 0;
  {
    final Iterator it = _attributesEnabled.entrySet().iterator();
    while (it.hasNext()) {
      final Map.Entry pairs = (Map.Entry)it.next();
      final String thisName =  (String) pairs.getKey();
      final attributeEnabledStruct ae =  (attributeEnabledStruct) pairs.getValue();
      if (ae.value == false)
      {
        nDisabledAtt++;
      }
      if (program.getGPUAttribute(thisName) == null)
      {
        return false;
      }
    }
  }
  
  if (program.getGPUAttributesNumber() != (_attributesValues.size() + nDisabledAtt))
  {
    return false;
  }
  
  if (program.getGPUUniformsNumber() != _uniformValues.size())
  {
    return false;
  }
  
  {
    final Iterator it = _uniformValues.entrySet().iterator();
    while (it.hasNext()) {
      final Map.Entry pairs = (Map.Entry)it.next();
      final String thisName =  (String) pairs.getKey();
      if (program.getGPUUniform(thisName) == null)
      {
        return false;
      }
    }
  }
  
  {
    final Iterator it = _attributesValues.entrySet().iterator();
    while (it.hasNext()) {
      final Map.Entry pairs = (Map.Entry)it.next();
      final String thisName =  (String) pairs.getKey();
      if (program.getGPUAttribute(thisName) == null)
      {
        return false;
      }
    }
  }
  
  return true;
#endif
}

