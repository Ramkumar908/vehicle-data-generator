import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@NgModule({
  declarations: [HeaderComponent],
  imports: [
     CommonModule,
     MatButtonModule, 
     MatDividerModule,
     MatTableModule,
     MatSortModule,
     MatPaginatorModule,
     MatIconModule,
     MatDialogModule,
     MatProgressSpinnerModule,
     MatAutocompleteModule,
     FormsModule,
     ReactiveFormsModule
  ],
  exports: [
    HeaderComponent, 
    MatButtonModule, 
    MatDividerModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatIconModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    MatAutocompleteModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class SharedModule {}
