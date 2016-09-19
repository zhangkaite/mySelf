#!/usr/bin/env python
#coding=utf8

import xlrd,os,shutil,re,sys

class bean:
  name = ""
  type = ""
  description = "" 
def __init__(strName,strType):
   self.name = strName
   self.type = strType
def setName(str):
   name = str
def getName():
   return name
def setType(str):
   type = str
def getType():
   return type

data = xlrd.open_workbook("/Users/zbs/Downloads/【支付】中心接口说明v2.0.xlsx")
startMark = "Request"
endMark = "响应数据"

def readExcel(num):
    table = data.sheets()[num]
    row = table.nrows
    list = []
    for i in range(row):
       if(i>=3):
         name = table.cell(i,0).value
         type = table.cell(i,2).value
         if(name=="响应数据"):
            break
         newBean = bean()
         newBean.name = name
         if(type == "number" or type == "unmber"):
            newBean.type = "Integer"
         elif(type == "number_b"):
            newBean.type = "BigInteger"
         elif(type == "List<number>" or type == "List<Number>"):
            newBean.type = "List<Integer>"
         elif(type == "List<string>" or type == "List<String>"):
            newBean.type = "List<String>"
         else:
            newBean.type = "String"
         newBean.description = table.cell(i,1).value   
         if(newBean.name!="" and newBean.type!=""):
            list.append(newBean)
    return list

def getClassDes(num):
    table = data.sheets()[num]
    return table.cell(0,5).value

#------- 得到excel文件
def getSheetName(num):
    table = data.sheets()[num]
    temp = table.cell(0,1).value
    return temp

#----------------
def hasChines(str):
    uni = unicode(str)
    zh = re.compile(u'[\u4e00-\u9fa5]+')
    match = zh.search(uni)
    res = True
    if match:
       res = False
    return res

#------ 构建java文件中的package -----------
def getPackage(path):
    temp = path.replace("/",".")
    temp = temp.split('com.')
    if len(temp) == 2:
        return "package com."+temp[1]+";"

#------ 得到java class文件的path路径 -----------
def getClassPath(path,name):
    temp = path.replace("/",".")
    temp = temp.split('com.')
    if len(temp) == 2:
        return "com."+temp[1]+"."+firstLetter(name)


#------ 创建java文件，并写入内容 ----------
def createJava(list,name,xmlFile,des):
   path = os.getcwd()
   package = getPackage(path)
   writeXML(name,xmlFile,path,des)
   javaFile = open(path+"/"+firstLetter(name)+".java",'w')
   javaFile.write(package+"\n\n")
   writeImport(getTypeMap(list),javaFile)
   javaFile.write("\n/** \n * "+des+"\n **/\n\n")
   javaFile.write("public class "+firstLetter(name)+" {\n\n")
   writePro(list,javaFile)
   writeSetGet(list,javaFile)  
   javaFile.write("}\n")

#------  判断类里面拥有的类型 -----------
def getTypeMap(list):
    map={}
    for i in list:
         type = i.type.lower()
         if re.compile('list').search(type) != None:
           map["list"] = "true"
         elif re.compile('map').search(type) != None:
           map["map"] = "true"
         elif re.compile('biginteger').search(type) != None:
           map["biginteger"] = "true"
         elif re.compile('number_b').search(type)!= None:
           map["biginteger"] = "true"
         else:
           break        
    return map
     

#------  根据类型在java class中写路import
def writeImport(map,javaFile):
      if("list" in map):
         javaFile.write("import java.util.List;\n")
      if("map" in map):
         javaFile.write("import java.util.Map;\n")
      if("biginteger" in map):
         javaFile.write("import java.math.BigInteger;\n")
   
#------  在xml文件中写入bean  -----------
def writeXML(name,xmlFile,path,des):
    xmlFile.write("\n<!-- "+des+" --> \n")
    xmlFile.write("<bean id=\""+name+"Bean\" class=\""+getClassPath(path,name)+"\"  />\n") 

#----- set和get方法-----------
def writeSetGet(list,javaFile):
    for i in list:
       name = i.name
       type = i.type
       if hasChines(name):
          javaFile.write("public void set"+firstLetter(name)+" ("+type+" "+name+") {\n")
          javaFile.write("    this."+name+" = "+name+";\n")
          javaFile.write("}\n")
          javaFile.write("public "+type+" get"+firstLetter(name)+"() { \n")
          javaFile.write("     return this."+name+";\n")
          javaFile.write("}\n")

#------- 首字母大写-----------
def firstLetter(str):
    let = str[0].upper()+str[1:]          
    return let 

#------- 写属性 --------
def writePro(list,javaFile):
    for i in list:
       name = i.name
       type = i.type
       des = i.description
       if hasChines(name):
          if(name == endMark):
             break
          else:
             javaFile.write("private "+type+" "+name+"; //"+des+"\n")

#-----  打印通用域中的内容 ------
def writeGeneral():
    table = data.sheets()[3]
    row = table.nrows
    list = []
    for i in range(row):
       if(i>=2):
         name = table.cell(i,0).value
         type = table.cell(i,2).value
         if(name=="响应数据"):
            break
         newBean = bean()
         newBean.name = name
         if(type == "number" or type == "unmber"):
            newBean.type = "Integer"
         else:
            newBean.type = "String"
         newBean.description = table.cell(i,1).value   
         if(newBean.name!="" and newBean.type!=""):
            list.append(newBean)
    return list

def main():
     reload(sys)
     sys.setdefaultencoding('utf8')
     c = 3
     endNum = len(data.sheets())
     path = os.getcwd()
     xmlFile = open(path+"/sprint.xml",'w')
     list = writeGeneral()
     createJava(list,'generalPro',xmlFile,'通用域')
     while(c<endNum-1):
       c+=1
       list = readExcel(c)
       des = getClassDes(c)
       sheetName = getSheetName(c)
       print(sheetName)
       createJava(list,sheetName,xmlFile,des)

if __name__=="__main__":
     main()
