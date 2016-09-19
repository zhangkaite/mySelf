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

data = xlrd.open_workbook("/Users/zbs/Downloads/用户中心接口说明v2.0.xlsx")
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
         else:
            newBean.type = "String"
         newBean.description = table.cell(i,1).value   
         if(newBean.name!="" and newBean.type!=""):
            list.append(newBean)
    return list

def getClassDes(num):
    table = data.sheets()[num]
    return table.cell(0,5).value


def getSheetName(num):
    table = data.sheets()[num]
    temp = table.cell(0,1).value
    return temp

def hasChines(str):
    uni = unicode(str)
    zh = re.compile(u'[\u4e00-\u9fa5]+')
    match = zh.search(uni)
    res = True
    if match:
       res = False
    return res

def getPackage(path):
    temp = path.replace("/",".")
    temp = temp.split('com.')
    if len(temp) == 2:
        return "package com."+temp[1]+";"

def getClassPath(path,name):
    temp = path.replace("/",".")
    temp = temp.split('com.')
    if len(temp) == 2:
        return "com."+temp[1]+"."+firstLetter(name)



def createJava(list,name,xmlFile,des):
   path = os.getcwd()
   package = getPackage(path)
   writeXML(name,xmlFile,path,des)
   javaFile = open(path+"/"+firstLetter(name)+".java",'w')
   javaFile.write(package+"\n")
   javaFile.write("\n/** \n * "+des+"\n **/\n\n")
   javaFile.write("public class "+firstLetter(name)+" {\n")
   writePro(list,javaFile)
   writeSetGet(list,javaFile)  
   javaFile.write("}\n")

def writeXML(name,xmlFile,path,des):
    xmlFile.write("\n<!-- "+des+" --> \n")
    xmlFile.write("<bean id=\""+name+"Bean\" class=\""+getClassPath(path,name)+"\" scope=\"prototype\" />\n") 


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

def firstLetter(str):
    let = str[0].upper()+str[1:]          
    return let 

def writePro(list,javaFile):
    for i in list:
       name = i.name
       type = i.type
       des = i.description
       if hasChines(name):
          if(name == endMark):
             break
          else:
             javaFile.write("public "+type+" "+name+"; //"+des+"\n")

#打印通用域中的内容
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
