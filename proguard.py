# coding: utf8

import sys, getopt
import os
import subprocess
import shutil
import lzma
from optparse import OptionParser
from optparse import OptionGroup
#  shell process 

usage = 'Usage: python3 proguard.py -b branch'
parser = OptionParser(usage,version='%prog 1.0')
parser.add_option('-b','--branch',action='store_true',help="decode mapping file of project branch")
(options,args) = parser.parse_args()


def moveAndUnzip(mappingFileName,branch):
    print("**** subprocess.run ****")
    input_path = "/Users/zhangpan/Downloads/%s" % (mappingFileName)
    if os.path.isfile(input_path):
        shutil.copy(input_path,"/Users/zhangpan/work/gitwork/BubbleAlbum/mapping/%s/" % (branch))
    else:
        print("Lose: " + input_path)
        return
    print(subprocess.check_call(['pwd']))
    xzFile = "/Users/zhangpan/work/gitwork/BubbleAlbum/mapping/%s/%s" % (branch,mappingFileName)
    txtFile =  ".".join(xzFile.split('.')[0:-1])
    with lzma.open(xzFile,'rb') as input:
        with open(txtFile,'wb') as output:
            shutil.copyfileobj(input,output)


def main(argv):
    try:
        opts, args = getopt.getopt(argv,"b:",["branch="])
    except expression as identifier:
        print ('test.py -i <inputfile> -o <outputfile>')
        sys.exit(2)
    for opt, arg in opts:
        if opt in ("-b", "--branch"):
            print(arg)
    # moveAndUnzip("mapping.txt.xz","din")

if __name__ == "__main__":
    main(sys.argv[1:])
    
    # print(".".join(os.path.basename('0.test.md').split('.')[0:-1]))