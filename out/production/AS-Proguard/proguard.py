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
parser.add_option('-t','--type',action='store_true',help="decode mapping file type(log,keyword)")
parser.add_option('-b','--branch',action='store_true',help="decode mapping file of project branch")
parser.add_option('-k','--keyword',action='store_true',help="decode mapping file of project keyword")
(options,args) = parser.parse_args()

def searchKeyword(branch,keyword):
    resp={}
    result = []
    p = subprocess.Popen('less /Users/zhangpan/work/gitwork/BubbleAlbum/mapping/%s/mapping.txt  | grep %s  -C2' % (branch,keyword), shell=True, stdout=subprocess.PIPE)
    for line in p.stdout.readlines():
        result.append(str(line,encoding = "utf-8"))
    retval = p.wait()
    resp['code'] = 0
    resp['msg'] = "success"
    resp['data'] = result
    return resp


def moveAndUnzip(mappingFileName,branch):
    resp={}
    input_path = "/Users/zhangpan/Downloads/%s" % (mappingFileName)
    des_path = "/Users/zhangpan/work/gitwork/BubbleAlbum/mapping/%s/" % (branch)
    if os.path.isdir(des_path):
        temp = 1
    else:
        os.makedirs(des_path)
    if os.path.isfile(input_path) and os.path.isdir(des_path):
        shutil.move(input_path,"/Users/zhangpan/work/gitwork/BubbleAlbum/mapping/%s/" % (branch))
    else:
        resp['code'] = 1
        resp['msg'] = "failed"
        resp['data'] = "Lose: %s or %s is error " % (input_path,des_path)
        return resp
    xzFile = "/Users/zhangpan/work/gitwork/BubbleAlbum/mapping/%s/%s" % (branch,mappingFileName)
    txtFile =  ".".join(xzFile.split('.')[0:-1])
    with lzma.open(xzFile,'rb') as input:
        with open(txtFile,'wb') as output:
            shutil.copyfileobj(input,output)
            resp['code'] = 0
            resp['msg'] = "success"
            resp['data'] = ""
            return resp
    resp['code'] = 1
    resp['msg'] = "failed"
    resp['data'] = "unknown error"
    return resp


def main(argv):
    branch = ''
    decodeType = -1
    keyword = ''
    resp = {}
    resp['code'] = 1
    resp['msg'] = "check your parmeter -t -b -k"
    resp['data'] = ""
    try:
        opts, args = getopt.getopt(argv,"hb:t:k:",["branch=","type=","keyword="])
    except Exception :
        print ('proguard.py -t <type> -b <branch> -k <keyword>')
        sys.exit(2)
    for opt, arg in opts:
        if opt in ("-b", "--branch"):
            branch = arg
            # print(moveAndUnzip("mapping.txt.xz",arg))
        elif opt in ("-k", "--keyword"):
            keyword = arg
            # print(searchKeyword(arg))
        elif opt in ('-t','--type'):
            decodeType = int(arg)
    # print(branch)
    # print(int(decodeType) == 1 )
    # print(keyword)
    if decodeType == 0:
        if branch == "":
            print(resp)
        else :
            print(moveAndUnzip("mapping.txt.xz",branch))
    elif decodeType == 1:
        if branch == "" == keyword == "" :
            print(resp)
        else :
            print(searchKeyword(branch,keyword))
    else:
        print(resp)
    # moveAndUnzip("mapping.txt.xz","din")

if __name__ == "__main__":
    main(sys.argv[1:])
    # print(searchKeyword("v3.5.5","rr2"))
    
    # print(".".join(os.path.basename('0.test.md').split('.')[0:-1]))