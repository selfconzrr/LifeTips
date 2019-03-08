#! /usr/bin/python
# -*- coding: utf-8 -*-

import os
import os.path
import tinify
import xlwt
import xlrd
import time

tinify.key = "*************" // 替换为自己购买的 key

def get_now():
    return time.strftime("%Y_%m_%d", time.localtime())

def compress(path):
    filename_tinyed = xlwt.Workbook()
    sheet_tinyed = filename_tinyed.add_sheet('TinyPicture%s' % get_now())
    filename_notiny = xlwt.Workbook()
    sheet_notiny = filename_notiny.add_sheet('noTiny%s' % get_now())

    row = 0
    column = 0
    sheet_tinyed.write(row, 0, u'已经压缩图片路径')
    sheet_tinyed.write(row, 1, u'原始大小')
    sheet_tinyed.write(row, 2, u'mini_size')
    sheet_tinyed.write(row, 3, u'压缩率')
    row += 1
    sheet_notiny.write(column, 0, u'没有替换图片路径（压缩率<10%）')
    sheet_notiny.write(column, 1, u'原始大小')
    sheet_notiny.write(column, 2, u'mini_size')
    sheet_notiny.write(column, 3, u'压缩率')
    column += 1

    total_remove_size = 0

    for dir_path, dir_names, file_names in os.walk(path):

        build_exclude = path + '/build'
        file_names = filter(lambda file_name: file_name[-4:] == '.png' and file_name[-6:] != '.9.png' and file_name != 'phototemp.png' and dir_path != build_exclude, file_names)
        file_names = map(lambda file_name: os.path.join(dir_path, file_name), file_names)

        for file in file_names:
            i = 0
            original_size = os.path.getsize(file)

            with open('whitelist.txt', 'r') as foo:
                for line in foo.readlines():
                    if file in line and str(original_size) in line:
                        i = 1
                        break
            if i == 1:
                continue

            pathtemp = 'phototemp.png'
            source = tinify.from_file(file)
            source.to_file(pathtemp)
            mini_size = os.path.getsize(pathtemp)
            remove_rate = round((original_size - mini_size) * 1.0 / original_size ,3)

            if remove_rate > 0.1:
                source.to_file(file)
                sheet_tinyed.write(row, 0, file)
                sheet_tinyed.write(row, 1, original_size)
                sheet_tinyed.write(row, 2, mini_size)
                sheet_tinyed.write(row, 3, remove_rate)
                row += 1
                total_remove_size += original_size - mini_size
            else:
                sheet_notiny.write(column, 0, file)
                sheet_notiny.write(column, 1, original_size)
                sheet_notiny.write(column, 2, mini_size)
                sheet_notiny.write(column, 3, round(remove_rate,3))
                column += 1
                with open('whitelist.txt', 'a') as foo:
                    foo.write(file)
                    foo.write(',')
                    foo.write(str(original_size))
                    foo.write('\n')


    sheet_tinyed.write(row, 0, u'总共缩减图片大小：'+str(total_remove_size)+u'byte')
    filename_tinyed.save('TinyPicture_%s.xls'% get_now())
    filename_notiny.save('noTiny_%s.xls'% get_now())
    print(total_remove_size)


if __name__ == '__main__':
    compress(os.getcwd())   #当前工作目录

