#! /bin/bash

# This git pre commit hook is intended to work on both cygwin and unix
# machines.
# It should be symlinked to ../../.git/hooks/pre-commit.

# Halt on error.
set -e
set -x

# Go to execution directory.
# cd $(dirname $0)
if [ -z "$LEARNING_SCALA_CHAPTER" ];
then
    make clean
else
    make clean_fpis_chapter
fi

git diff --name-only --cached \
    | grep '\.scala' \
    | parallel \
        -I % \
        --verbose \
        --jobs $((2*$(nproc))) \
        "vim -i NONE -c 'VimScalafmt' -c 'noautocmd x!' %"

# https://stackoverflow.com/questions/3466166/how-to-check-if-running-in-cygwin-mac-or-linux
cd ./scala_initiatives/
if [[ $(uname -s) == CYGWIN* ]];then
    # Use 'test' for full tests and 'testQuick' for incremental testing.
    sbt.bat testQuick
else
    # Use 'test' for full tests and 'testQuick' for incremental testing.
    sbt testQuick
fi

Where the relevant vim code is:

# Commit: 'c61d068' from vim_scala_plugin.
#
# augroup vim_scala_plugin
#     autocmd!
#     autocmd FileType scala command! -nargs=? VimScalafmt call s:VimScalafmt(<f-args>)
# augroup END
#
# " ???: let g:vim_scala_plugin_scalafmt_file = ???
#
# function! s:VimScalafmt(...)
#
#     function! LocalSubstitute(str, pat, subs)
#         return substitute(a:str, a:pat, a:subs, 'g')
#     endfunction
#
#     " Set optional arguments.
#     "
#     " First argument is the path to the scalafmt config file.
#     if a:0 == 0
#         if exists("g:vim_scala_plugin_scalafmt_file")
#             let l:conffile = g:vim_scala_plugin_scalafmt_file
#         else
#             let l:conffile = ''
#         endif
#     else
#         let l:conffile = a:1
#     endif
#
#     if l:conffile == ''
#         let l:confcommand = ''
#     else
#         let l:confcommand = '--config ' . l:conffile . ' '
#     endif
#
#     let l:scalafmt_command = "scalafmt "
#         \ . l:confcommand
#         \ . "--quiet "
#         \ . "--non-interactive "
#         \ . "--stdin "
#         \ . "--stdout"
#
#     " let l:post_execution = "| "
#     " let l:post_execution = ""
#     let l:post_execution = " 2>/dev/null"
#
#     let l:execute_string = l:scalafmt_command . l:post_execution
#     " let l:execute_string = shellescape(l:scalafmt_command . l:post_execution)
#
#     " echom l:post_execution_sed
#     " echom l:execute_string
#
#     " ???: This is a workaround for:
#     " https://github.com/scalameta/scalafmt/issues/1382
#     " {
#     let l:buffer_content = join(getline(1,'$'), "\n")
#     let l:stdout = systemlist(l:execute_string, l:buffer_content)
#
#     if l:stdout == []
#         echoerr "There were errors when running VimScalafmt."
#         return -1
#     endif
#
#     let l:joined_out = join(l:stdout, "\n")
#
#     " [[:space:]]
#     let l:nobreak_after_arrows = substitute(
#         \ l:joined_out,
#         \ '⇒\n\s*',
#         \ '⇒ ',
#         \ 'g')
#
#     let l:nobreak_as_list = split(l:nobreak_after_arrows, "\n")
#     " }
#
#     call setline(1, l:nobreak_as_list)
#
# endfunction

# vim: set filetype=sh fileformat=unix wrap:
