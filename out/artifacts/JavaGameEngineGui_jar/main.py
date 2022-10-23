import os
import time
import pathlib

path = "./scripts/"

files = {}

while ( True ):
    for filename in os.listdir(path):     
        lastTime = (os.path.getmtime(path+filename))
        if filename in files:
            if files[filename] < lastTime:
                print("compiling "+filename)
                os.system('javac -cp D:\dev\java\JavaGameEngineNew\out\\artifacts\JavaGameEngineNew_jar\JavaGameEngineNew.jar .\scripts\MyScript.java')
                print("done compiling "+filename)
        sufix = pathlib.Path(filename).suffix
        
        if sufix != ".class":            
            files[filename] = lastTime
        
        
    time.sleep(5)