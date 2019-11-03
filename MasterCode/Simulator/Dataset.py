from rangedetector import draw_many_rectangle
import  random
min_detector=5
max_detector=10
size=3000
directory="RangeTarget/"

import os

#min & max range detector for target
def Many_environnement_with_different_nbtarget(nb_matrix_wanted):

    #random range for  target detection


     if not os.path.exists(directory):
          os.makedirs(directory)

     for i in range(1,5):

      if not os.path.exists(directory+str(i)):
         os.makedirs(directory+str(i))

      for l in range(1,3):
           if not os.path.exists(directory + str(i)+"/mat"+str(l)):
              os.makedirs(directory + str(i)+"/mat"+str(l))
              #mono_target
           for r in range(1, 4):
               if not os.path.exists(directory + str(i) + "/mat" + str(l)+"/"+str(r*min_detector)):
                   os.makedirs(directory + str(i) + "/mat" + str(l)+"/"+str(r*min_detector))
                   # mono_target
               nb = i
               s = draw_many_rectangle(directory + str(i) + "/mat" + str(l) +"/"+str(r*min_detector)+ "/target" + str(nb) + ".png",
                                       max_detector, min_detector*r, nb, size)
               file = open(directory + str(i) + "/mat" + str(l) + "/"+str(r*min_detector)+"/target" + str(nb) + ".txt", "w").write(s)






if __name__ == '__main__':
 Many_environnement_with_different_nbtarget(5)
