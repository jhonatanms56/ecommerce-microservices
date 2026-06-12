import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { OrderService } from '../../core/services/order.service';
import { OrderResponse } from '../../models/order.model';

@Component({
  selector: 'app-order-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatDividerModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './order-form.component.html',
  styleUrl: './order-form.component.scss'
})
export class OrderFormComponent {
  form: FormGroup;
  submitting = signal(false);
  error = signal('');
  success = signal<OrderResponse | null>(null);

  constructor(private fb: FormBuilder, private orderService: OrderService) {
    this.form = this.fb.group({
      customerId: ['', Validators.required],
      items: this.fb.array([this.createItem()])
    });
  }

  get items(): FormArray {
    return this.form.get('items') as FormArray;
  }

  createItem(): FormGroup {
    return this.fb.group({
      productId: ['', Validators.required],
      productName: ['', Validators.required],
      quantity: [1, [Validators.required, Validators.min(1)]],
      unitPrice: [null, [Validators.required, Validators.min(0.01)]]
    });
  }

  addItem(): void {
    this.items.push(this.createItem());
  }

  removeItem(index: number): void {
    if (this.items.length > 1) {
      this.items.removeAt(index);
    }
  }

  submit(): void {
    if (this.form.invalid) return;

    this.submitting.set(true);
    this.error.set('');
    this.success.set(null);

    this.orderService.createOrder(this.form.value).subscribe({
      next: (order) => {
        this.success.set(order);
        this.submitting.set(false);
        this.form.reset();
        this.items.clear();
        this.items.push(this.createItem());
      },
      error: () => {
        this.error.set('Failed to create order. Please try again.');
        this.submitting.set(false);
      }
    });
  }
}
