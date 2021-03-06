package com.github.syuchan1005.gitprefix.filetype;

import com.github.syuchan1005.gitprefix.icons.GitPrefixIcons;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class PrefixNewFileAction extends AnAction {
	public PrefixNewFileAction() {
		super("GitPrefix", null, GitPrefixIcons.FILE_ICON);
	}

	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		Project project = e.getProject();
		if (project == null) return;
		IdeView ideView = e.getRequiredData(LangDataKeys.IDE_VIEW);
		PsiDirectory directory = ideView.getOrChooseDirectory();
		if (directory != null) {
			VirtualFile file = null;
			String ext = "." + PrefixResourceFileType.DEFAULT_EXTENSION;
			WriteCommandAction.runWriteCommandAction(project, () -> {
				if (directory.findFile(ext) == null) directory.createFile(ext);
			});
			PsiFile psiFile = directory.findFile(ext);
			if (psiFile != null) file = psiFile.getVirtualFile();
			if (file != null) FileEditorManager.getInstance(project).openFile(file, true);
		}
	}

	@Override
	public void update(@NotNull AnActionEvent e) {
		Project project = e.getProject();
		IdeView ideView;
		try {
			ideView = e.getRequiredData(LangDataKeys.IDE_VIEW);
		} catch (Throwable ignored) {
			return;
		}
		PsiDirectory[] directories = ideView.getDirectories();
		if (directories.length == 0 || project == null) {
			e.getPresentation().setVisible(false);
		}
	}
}
