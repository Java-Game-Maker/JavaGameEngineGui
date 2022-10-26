import os
import time
import pathlib

path = "./scripts/"

files = {}
javacPath = '"/usr/lib/jvm/java-17-graalvm/bin/javac"'
libPath = "/home/spy/dev/JavaGameMaker/JavaGameEngine/out/artifacts/JavaGameEngine_jar/JavaGameEngine.jar"
while ( True ):
    for filename in os.listdir(path):     
        lastTime = (os.path.getmtime(path+filename))
        if filename in files:
            if files[filename] < lastTime:
                print("compiling "+filename)
                os.system(''+javacPath+' -cp '+libPath+' ./scripts/MyScript.java')
                print("done compiling "+filename)
        sufix = pathlib.Path(filename).suffix
        
        if sufix != ".class":            
            files[filename] = lastTime
        
        
    time.sleep(5)