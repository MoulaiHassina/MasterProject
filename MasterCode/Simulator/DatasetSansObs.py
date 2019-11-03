#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Apr  3 00:39:47 2019

@author: izan
"""



#min & max range detector for target
def Many_environnement_with_different_size(sizeList,nb,directory,max_detector,min_detector):
    
    if not os.path.exists(directory):
          os.makedirs(directory)
          
    for l in range(0,10):
        size = sizeList[l]
        portee = int(size/10)#porteeList[l]
        print(size," : ",portee)
        
        if not os.path.exists(directory + "/size"+str(size)):
            os.makedirs(directory + "/size"+str(size))
            
            
        for r in range(1, 5):
            if not os.path.exists(directory + "/size"+str(size) + "/mat" + str(r)):
                os.makedirs(directory + "/size"+str(size) + "/mat" + str(r))
            
            s = draw_many_rectangle(directory + "/size"+str(size) +"/mat" + str(r)+ "/target" + str(nb) + ".png",
                                       max_detector, portee, nb, size)
            file = open(directory  + "/size"+str(size) +"/mat" + str(r)+ "/target" + str(nb) + ".txt", "w").write(s)


def Many_environnement_with_different_portee(PorteeList,nb,directory,max_detector,min_detector):
    
    size = 500
    if not os.path.exists(directory):
          os.makedirs(directory)
          
    for l in range(0,10):
#        size = PorteeList[l]
        portee = PorteeList[l]
        
        if not os.path.exists(directory + "/portee"+str(portee)):
            os.makedirs(directory + "/portee"+str(portee))
            
            
        for r in range(1, 5):
            if not os.path.exists(directory + "/portee"+str(portee) + "/mat" + str(r)):
                os.makedirs(directory + "/portee"+str(portee) + "/mat" + str(r))
            
            s = draw_many_rectangle(directory + "/portee"+str(portee) +"/mat" + str(r)+ "/target" + str(nb) + ".png",
                                       max_detector, portee, nb, size)
            file = open(directory  + "/portee"+str(portee) +"/mat" + str(r)+ "/target" + str(nb) + ".txt", "w").write(s)


def Many_environnement_with_different_Target(portee,size,nbList,directory,max_detector,min_detector):
    

    if not os.path.exists(directory):
          os.makedirs(directory)
          
    for l in range(0,len(nbList)):
        nb=nbList[l]
        
        if not os.path.exists(directory + "/nbTargets"+str(nb)):
            os.makedirs(directory + "/nbTargets"+str(nb))
            
            
        for r in range(1, 5):
            if not os.path.exists(directory + "/nbTargets"+str(nb) + "/mat" + str(r)):
                os.makedirs(directory + "/nbTargets"+str(nb) + "/mat" + str(r))
            
            s = draw_many_rectangle(directory + "/nbTargets"+str(nb) +"/mat" + str(r)+ "/target" + str(nb) + ".png",
                                       max_detector, portee, nb, size)
            file = open(directory  + "/nbTargets"+str(nb) +"/mat" + str(r)+ "/target" + str(nb) + ".txt", "w").write(s)



from rangedetector import draw_many_rectangle

min_detector=0
max_detector=0

sizeList = [50,100,200,400,600,800,1000,1500,3000,5000] 
Portee = [10,20,30,40,50,60,70,80,90,100] #
nbList = [1,3,5,7,9,11,13,15]
nb = 5
direct= "SansObs" #"AvecObs"
directory1=direct+"/SIZE/"+str(nb)+"targets"
directory2=direct+"/Portee/"+str(nb)+"targets"
directory3=direct+"/nbTarget"

import os


if __name__ == '__main__':
# Many_environnement_with_different_size(sizeList,nb,directory1,max_detector,min_detector)
  Many_environnement_with_different_portee(Portee,nb,directory2,max_detector,min_detector)
# Many_environnement_with_different_Target(50,500,nbList,directory3,max_detector,min_detector)
